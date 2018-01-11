// See LICENSE for license details.
package fpga

import chisel3._
import chisel3.experimental.DataMirror
import chisel3.core.ActualDirection
import chisel3.util.MuxLookup

class MemoryMappedDevice(deviceGenerator: => Module, memoryWidth: Int) extends Module {
  val io = IO(new AvalonMaster)
  val device = Module(deviceGenerator)

  private def roundUp(value: Int, nearest: Int): Int = {
    if (value % nearest == 0) {
      value
    } else {
      (((value + nearest) / nearest) * nearest)
    }
  }

  // Generate C++ code
  val fields = new Array[CppField](device.io.elements.size)
  var i: Int = 0
  var offset: Int = 0 // in multiples of memoryWidth

  for ((name, definition) <- device.io.elements) {
    // find nearest compatible c++ type
    val ctype = definition.getWidth match {
      case 1 => "bool"
      case x if x <= 8 => "uint8_t"
      case x if x <= 16 => "uint16_t"
      case x if x <= 32 => "uint32_t"
      case x if x <= 64 => "uint64_t"
      case _ => ""
    }

    // get the offset to the nearest byte
    fields(i) = new CppField(name, ctype, offset / 8, definition)
    offset = offset + roundUp(definition.getWidth, memoryWidth)
    i = i + 1
  }

  val rendered = txt.MappedInterface(name, fields)
  println(rendered)

  // This means that we need `offset` lots of `memoryWidth` registers to
  // keep track of the state. 
  // TODO: only store values for inputs, we don't need to store outputs.
  val regs = Reg(Vec(offset / memoryWidth, UInt(memoryWidth.W)))

  // now mux the values in to the specified register
  when(io.ready) {
    io.read_data := MuxLookup(io.address, 0.U, fields.map (field => {
       if (DataMirror.directionOf(field.element) == ActualDirection.Output) {
         field.offset.U -> field.element
       } else {
         field.offset.U -> regs((field.offset / 4).U )
       }
    }))
  }.otherwise {
    io.read_data := DontCare
  }

  // write data as appropriate
  when (io.ready) {
    when (!io.write_n) {
      // TODO: check whether this is write_n or write
      regs(io.address(9, 2)) := io.write_data
    }
  }

  for (i <- fields.indices) {
    val field = fields(i)

    if (DataMirror.directionOf(field.element) == ActualDirection.Input) {
      field.element := regs(i)
    }
  }
}
