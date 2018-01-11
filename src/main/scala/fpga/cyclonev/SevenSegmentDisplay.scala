// See LICENSE for license details.
package fpga.cyclonev

import chisel3._

class SevenSegmentDisplay extends Module {
  val io = IO(new Bundle {
    val value = Input(UInt(4.W))
    val display = Output(UInt(7.W))
  })

  private val connection =
    Array(0x40, 0x79, 0x24, 0x30, 0x19, 0x12, 0x2, 0x78, 0x0, 0x10, 0x8, 0x3, 0x46, 0x21, 0x6, 0xe)
  private val lut = VecInit(connection.map { _.asUInt(8.W) })

  io.display := lut(io.value)
}
