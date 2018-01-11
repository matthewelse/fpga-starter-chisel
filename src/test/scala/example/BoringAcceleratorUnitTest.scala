// See LICENSE for license details.
package example

import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

class BoringAcceleratorUnitTest(dut: BoringAccelerator) extends PeekPokeTester(dut) {
  for (i <- 0 to 256) {
    poke(dut.io.switches, i)
    expect(dut.io.leds, i)
  }

  expect(dut.io.displays(0), 0xFF)
  expect(dut.io.displays(1), 0x00)
  expect(dut.io.displays(2), 0x01)
}

class BoringAcceleratorTester extends ChiselFlatSpec {
  val wordSize = 32

  Driver(() => new BoringAccelerator, "firrtl") {
    c => new BoringAcceleratorUnitTest(c)
  } should be (true)
}
