// See LICENSE for license details.
package fpga

import chisel3._

trait Accelerator extends Module {
  val io = IO(new Bundle {
    // avalon master
    val memory = new AvalonMaster()

    // common io
    val leds = Output(UInt(10.W))
    val displays = Output(Vec(3, UInt(8.W)))

    val switches = Input(UInt(10.W))
    val buttons = Input(UInt(4.W))
  })
}
