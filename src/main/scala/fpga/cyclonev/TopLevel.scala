// See LICENSE for license details.
package fpga.cyclonev

import chisel3._
import chisel3.experimental.{RawModule, withClockAndReset}
import fpga.Accelerator

class TopLevel(generateAccelerator: => Accelerator) extends RawModule {
  val io = IO(new Bundle {
    val CLOCK_50 = Input(Clock())

    val HEX = Output(Vec(6, UInt(7.W)))

    val HPS_DDR3 = new DDRMemory()
    val HPS_ENET = new HPSEthernet()
    val HPS_SD = new HPSSDIO()
    val HPS_UART = new HPSUART()
    val HPS_USB = new HPSUSB()

    val KEY = Input(UInt(4.W))
    val LEDR = Output(UInt(10.W))
    val SW = Input(UInt(10.W))
  })

  val hps = Module(new HPS())

  hps.io.reset.rst := true.B
  hps.io.clk.clk <> io.CLOCK_50

  hps.io.hps_io_hps_io.emac1_inst <> io.HPS_ENET
  hps.io.hps_io_hps_io.sdio_inst <> io.HPS_SD
  hps.io.hps_io_hps_io.uart0_inst <> io.HPS_UART
  hps.io.hps_io_hps_io.usb1_inst <> io.HPS_USB
  hps.io.mem <> io.HPS_DDR3

  withClockAndReset(io.CLOCK_50, true.B) {
    val accelerator = Module(generateAccelerator)

    io.KEY <> accelerator.io.buttons
    io.LEDR <> accelerator.io.leds
    io.SW <> accelerator.io.switches

    for (i <- 0 until accelerator.io.displays.length) {
      val translatorLSB = Module(new SevenSegmentDisplay())
      val translatorMSB = Module(new SevenSegmentDisplay())

      // TODO: check the ordering
      io.HEX(2 * i) := translatorLSB.io.display
      io.HEX(2 * i + 1) := translatorMSB.io.display

      translatorLSB.io.value := accelerator.io.displays(i)(3, 0)
      translatorMSB.io.value := accelerator.io.displays(i)(7, 4)
    }
  }
}
