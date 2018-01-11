// See LICENSE for license details.
package fpga.cyclonev

import chisel3._
import chisel3.experimental.Analog

class HPSClock extends Bundle {
  val clk = Input(Clock())
}

class HPSReset extends Bundle {
  val rst = Input(Bool())
}

class HPSEthernet extends Bundle {
  val TX_CLK = Output(Bool())
  val TX_CTL = Output(Bool())
  val TXD0 = Output(Bool())
  val TXD1 = Output(Bool())
  val TXD2 = Output(Bool())
  val TXD3 = Output(Bool())

  val RX_CTL = Input(Bool())
  val RX_CLK = Input(Bool())
  val RXD0 = Input(Bool())
  val RXD1 = Input(Bool())
  val RXD2 = Input(Bool())
  val RXD3 = Input(Bool())

  val MDIO = Input(Bool())
  val MDC = Output(Bool())
}

class HPSSDIO extends Bundle {
  val CMD = Analog(1.W)
  val DATA = Analog(4.W)
  val CLK = Output(Clock())
}

class HPSUSB extends Bundle {
  val CLK = Output(Clock())
  val DATA = Analog(8.W)
  val DIR = Input(Bool())
  val NXT = Input(Bool())
}

class HPSUART extends Bundle {
  val TX = Output(Bool())
  val RX = Input(Bool())
}

class HPSIO extends Bundle {
  val emac1_inst = new HPSEthernet()
  val sdio_inst = new HPSSDIO()
  val usb1_inst = new HPSUSB()
  val uart0_inst = new HPSUART()
}

class DDRMemory extends Bundle {
  val mem_a = Output(UInt(15.W))
  val mem_ba = Output(UInt(3.W))
  val mem_cas_n = Output(Bool())
  val mem_cke = Output(Bool())
  val mem_ck_n = Output(Bool())
  val mem_ck = Output(Bool())
  val mem_cs_n = Output(Bool())
  val mem_dm = Analog(4.W)
  val mem_dq = Analog(32.W)
  val mem_dqs_n = Output(UInt(4.W))
  val mem_dqs = Output(UInt(4.W))
  val mem_odt = Output(Bool())
  val mem_ras_n = Output(Bool())
  val mem_reset_n = Output(Bool())
  val oct_rzqin = Input(Bool())
  val mem_we_n = Output(Bool())
}

class HPS extends BlackBox {
  val io = IO(new Bundle {
    val clk = new HPSClock()
    val reset = new HPSReset()

    val mem = new DDRMemory()
    val hps_io_hps_io = new HPSIO()
  })
}
