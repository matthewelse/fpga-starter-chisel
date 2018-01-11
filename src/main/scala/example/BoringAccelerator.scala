// See LICENSE for license details.
package example

import chisel3._
import fpga.Accelerator

class BoringAccelerator extends Accelerator {
  io.memory <> DontCare
  io.leds <> io.switches
  io.displays(0) := 0xFF.U
  io.displays(1) := 0x00.U
  io.displays(2) := 0x01.U
}
