// See LICENSE for license details.
package fpga

import chisel3._

// TODO: parameterise memory width and size
class AvalonMaster extends Bundle {
    val ready = Input(Bool())
    val address = Input(UInt(10.W))
    val write_data = Input(UInt(32.W))
    val write_n = Input(Bool())
    

    val read_data = Output(UInt(32.W))
}
