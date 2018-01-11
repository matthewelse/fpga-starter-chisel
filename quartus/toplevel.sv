/* Copyright 2017 Matthew Else */
`define ENABLE_HPS

module toplevel(
  // Analogue-digital converter
  inout              ADC_CS_N,
  output             ADC_DIN,
  input              ADC_DOUT,
  output             ADC_SCLK,

  // Audio DAC
  input              AUD_ADCDAT,
  inout              AUD_ADCLRCK,
  inout              AUD_BCLK,
  output             AUD_DACDAT,
  inout              AUD_DACLRCK,
  output             AUD_XCK,

  // Clocks
  input              CLOCK_50,
  input              CLOCK2_50,
  input              CLOCK3_50,
  input              CLOCK4_50,

  // FPGA-side SDRAM
  output      [12:0] DRAM_ADDR,
  output       [1:0] DRAM_BA,
  output             DRAM_CAS_N,
  output             DRAM_CKE,
  output             DRAM_CLK,
  output             DRAM_CS_N,
  inout       [15:0] DRAM_DQ,
  output             DRAM_LDQM,
  output             DRAM_RAS_N,
  output             DRAM_UDQM,
  output             DRAM_WE_N,

  // Fan control (unused on native board)
  output             FAN_CTRL,

  // FPGA I2C
  output             FPGA_I2C_SCLK,
  inout              FPGA_I2C_SDAT,

  // General purpose I/O
  inout      [35:0]  GPIO_0,
  inout      [35:0]  GPIO_1,

  // Hex LEDs
  output      [6:0]  HEX0,
  output      [6:0]  HEX1,
  output      [6:0]  HEX2,
  output      [6:0]  HEX3,
  output      [6:0]  HEX4,
  output      [6:0]  HEX5,
		
`ifdef ENABLE_HPS
  // ARM Cortex A9 Hard Processor System
  inout              HPS_CONV_USB_N,
  output      [14:0] HPS_DDR3_ADDR,
  output      [2:0]  HPS_DDR3_BA,
  output             HPS_DDR3_CAS_N,
  output             HPS_DDR3_CKE,
  output             HPS_DDR3_CK_N,
  output             HPS_DDR3_CK_P,
  output             HPS_DDR3_CS_N,
  output      [3:0]  HPS_DDR3_DM,
  inout       [31:0] HPS_DDR3_DQ,
  inout       [3:0]  HPS_DDR3_DQS_N,
  inout       [3:0]  HPS_DDR3_DQS_P,
  output             HPS_DDR3_ODT,
  output             HPS_DDR3_RAS_N,
  output             HPS_DDR3_RESET_N,
  input              HPS_DDR3_RZQ,
  output             HPS_DDR3_WE_N,
  output             HPS_ENET_GTX_CLK,
  inout              HPS_ENET_INT_N,
  output             HPS_ENET_MDC,
  inout              HPS_ENET_MDIO,
  input              HPS_ENET_RX_CLK,
  input       [3:0]  HPS_ENET_RX_DATA,
  input              HPS_ENET_RX_DV,
  output      [3:0]  HPS_ENET_TX_DATA,
  output             HPS_ENET_TX_EN,
  inout       [3:0]  HPS_FLASH_DATA,
  output             HPS_FLASH_DCLK,
  output             HPS_FLASH_NCSO,
  inout              HPS_GSENSOR_INT,
  inout              HPS_I2C1_SCLK,
  inout              HPS_I2C1_SDAT,
  inout              HPS_I2C2_SCLK,
  inout              HPS_I2C2_SDAT,
  inout              HPS_I2C_CONTROL,
  inout              HPS_KEY,
  inout              HPS_LED,
  inout              HPS_LTC_GPIO,
  output             HPS_SD_CLK,
  inout              HPS_SD_CMD,
  inout       [3:0]  HPS_SD_DATA,
  output             HPS_SPIM_CLK,
  input              HPS_SPIM_MISO,
  output             HPS_SPIM_MOSI,
  inout              HPS_SPIM_SS,
  input              HPS_UART_RX,
  output             HPS_UART_TX,
  input              HPS_USB_CLKOUT,
  inout       [7:0]  HPS_USB_DATA,
  input              HPS_USB_DIR,
  input              HPS_USB_NXT,
  output             HPS_USB_STP,
`endif /*ENABLE_HPS*/

  // Infra-red
  input              IRDA_RXD,
  output             IRDA_TXD,

  // Push buttons on DE1-SoC mainboard
  input       [3:0]  KEY,

  // Red LED row
  output      [9:0]  LEDR,

  // PS2 port
  inout              PS2_CLK,
  inout              PS2_CLK2,
  inout              PS2_DAT,
  inout              PS2_DAT2,

  // Slide switches
  input       [9:0]  SW,

  // TMDS
  input              TD_CLK27,
  input       [7:0]  TD_DATA,
  input              TD_HS,
  output             TD_RESET_N,
  input              TD_VS,

  // VGA video
  output      [7:0]  VGA_B,
  output             VGA_BLANK_N,
  output             VGA_CLK,
  output      [7:0]  VGA_G,
  output             VGA_HS,
  output      [7:0]  VGA_R,
  output             VGA_SYNC_N,
  output             VGA_VS
);


TopLevel u0 (
  .io_CLOCK_50(CLOCK_50),
  .io_HEX_0(HEX0),
  .io_HEX_1(HEX1),
  .io_HEX_2(HEX2),
  .io_HEX_3(HEX3),
  .io_HEX_4(HEX4),
  .io_HEX_5(HEX5),
  .io_HPS_DDR3_mem_a(HPS_DDR3_ADDR),
  .io_HPS_DDR3_mem_ba(HPS_DDR3_BA),
  .io_HPS_DDR3_mem_cas_n(HPS_DDR3_CAS_N),
  .io_HPS_DDR3_mem_cke(HPS_DDR3_CKE),
  .io_HPS_DDR3_mem_ck_n(HPS_DDR3_CK_N),
  .io_HPS_DDR3_mem_ck(HPS_DDR3_CK_P),
  .io_HPS_DDR3_mem_cs_n(HPS_DDR3_CS_N),
  .io_HPS_DDR3_mem_dm(HPS_DDR3_DM),
  .io_HPS_DDR3_mem_dq(HPS_DDR3_DQ),
  .io_HPS_DDR3_mem_dqs_n(HPS_DDR3_DQS_N),
  .io_HPS_DDR3_mem_dqs(HPS_DDR3_DQS_P),
  .io_HPS_DDR3_mem_odt(HPS_DDR3_ODT),
  .io_HPS_DDR3_mem_ras_n(HPS_DDR3_RAS_N),
  .io_HPS_DDR3_mem_reset_n(HPS_DDR3_RESET_N),
  .io_HPS_DDR3_oct_rzqin(HPS_DDR3_RZQ),
  .io_HPS_DDR3_mem_we_n(HPS_DDR3_WE_N),
  .io_HPS_ENET_TX_CLK(HPS_ENET_GTX_CLK),
  .io_HPS_ENET_TX_CTL(HPS_ENET_TX_EN),
  .io_HPS_ENET_TXD0(HPS_ENET_TX_DATA[0]),
  .io_HPS_ENET_TXD1(HPS_ENET_TX_DATA[1]),
  .io_HPS_ENET_TXD2(HPS_ENET_TX_DATA[2]),
  .io_HPS_ENET_TXD3(HPS_ENET_TX_DATA[3]),
  .io_HPS_ENET_RX_CTL(HPS_ENET_RX_DV),
  .io_HPS_ENET_RX_CLK(HPS_ENET_RX_CLK),
  .io_HPS_ENET_RXD0(HPS_ENET_RX_DATA[0]),
  .io_HPS_ENET_RXD1(HPS_ENET_RX_DATA[1]),
  .io_HPS_ENET_RXD2(HPS_ENET_RX_DATA[2]),
  .io_HPS_ENET_RXD3(HPS_ENET_RX_DATA[3]),
  .io_HPS_ENET_MDIO(HPS_ENET_MDIO),
  .io_HPS_ENET_MDC(HPS_ENET_MDC),
  .io_HPS_SD_CMD(HPS_SD_CMD),
  .io_HPS_SD_D0(HPS_SD_DATA[0]),
  .io_HPS_SD_D1(HPS_SD_DATA[1]),
  .io_HPS_SD_D2(HPS_SD_DATA[2]),
  .io_HPS_SD_D3(HPS_SD_DATA[3]),
  .io_HPS_SD_CLK(HPS_SD_CLK),
  .io_HPS_UART_TX(HPS_UART_TX),
  .io_HPS_UART_RX(HPS_UART_RX),
  .io_HPS_USB_CLK(HPS_USB_CLKOUT),
  .io_HPS_USB_D0(HPS_USB_DATA[0]),
  .io_HPS_USB_D1(HPS_USB_DATA[1]),
  .io_HPS_USB_D2(HPS_USB_DATA[2]),
  .io_HPS_USB_D3(HPS_USB_DATA[3]),
  .io_HPS_USB_D4(HPS_USB_DATA[4]),
  .io_HPS_USB_D5(HPS_USB_DATA[5]),
  .io_HPS_USB_D6(HPS_USB_DATA[6]),
  .io_HPS_USB_D7(HPS_USB_DATA[7]),
  .io_HPS_USB_DIR(HPS_USB_DIR),
  .io_HPS_USB_NXT(HPS_USB_NXT),
  .io_HPS_USB_STP(HPS_USB_STP),
  .io_KEY(KEY),
  .io_LEDR(LEDR),
  .io_SW(SW)
);

/* connect all output pins to reduce unnecessary warnings */
`include "driver_stubs.sv"

endmodule

  