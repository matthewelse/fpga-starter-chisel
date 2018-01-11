// See LICENSE for license details.
package fpga

import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}
import gcd.{GCD}

class MemoryMappedUnitTester(dut: MemoryMappedDevice) extends PeekPokeTester(dut) {
  /**
    * compute the gcd and the number of steps it should take to do it
    *
    * @param a positive integer
    * @param b positive integer
    * @return the GCD of a and b
    */
  def computeGcd(a: Int, b: Int): (Int, Int) = {
    var x = a
    var y = b
    var depth = 1
    while(y > 0 ) {
      if (x > y) {
        x -= y
      }
      else {
        y -= x
      }
      depth += 1
    }
    (x, depth)
  }

  // assume we have a memory mapped gcd with the following layout:
  // offset: signature (0xDEADBEEF)
  // offset + 4: output valid
  // offset + 8: output GCD
  // offset + 12: loading values
  // offset + 16: value 2
  // offset + 20: value 1

  val signature = 0xDEADBEEFL

  val signatureOffset = 0
  val outputValidOffset = 4
  val gcdOffset = 8
  val loadingValuesOffset = 12
  val value2Offset = 16
  val value1Offset = 20
  val enableOffset = 24

  private def writeAddress(addr: Int, value: Int): Unit = {
    poke(dut.io.ready, 1)
    poke(dut.io.address, addr)
    poke(dut.io.write_n, 0)
    poke(dut.io.write_data, value)

    step(1)
  }

  private def readAddress(addr: Int) : BigInt = {
    poke(dut.io.ready, 1)
    poke(dut.io.address, addr)
    poke(dut.io.write_n, 1)
    step(1)
    peek(dut.io.read_data)
  }

  val deadbeef = readAddress(signatureOffset)
  expect(dut.io.read_data, deadbeef)


  writeAddress(enableOffset, 0)

  for(i <- 1 to 40 by 3) {
    for (j <- 1 to 40 by 7) {
      writeAddress(value1Offset, i)

      // Test that j has been successfully written.
      readAddress(value1Offset)
      expect(dut.io.read_data, i)

      writeAddress(value2Offset, j)

      // Test that j has been successfully written.
      readAddress(value2Offset)
      expect(dut.io.read_data, j)

      writeAddress(loadingValuesOffset, 1)
      step(1)
      writeAddress(loadingValuesOffset, 0)

      readAddress(loadingValuesOffset)
      expect(dut.io.read_data, 0)

      val (expected_gcd, steps) = computeGcd(i, j)

      writeAddress(enableOffset, 1)

      step(steps + 10) // -1 is because we step(1) already to toggle the enable

      writeAddress(enableOffset, 0)

      val out = readAddress(gcdOffset)
      expect(dut.io.read_data, expected_gcd)

      val out_valid = readAddress(outputValidOffset)
      expect(dut.io.read_data, 1)
    }
  }
}

/**
  * This is a trivial example of how to run this Specification
  * From within sbt use:
  * {{{
  * testOnly test.MemoryMappedTester
  * }}}
  * From a terminal shell use:
  * {{{
  * sbt 'testOnly test.MemoryMappedTester'
  * }}}
  */
class MemoryMappedTester extends ChiselFlatSpec {
  val wordSize = 32

  Driver(() => new MemoryMappedDevice(new GCD, wordSize), "firrtl") {
    c => new MemoryMappedUnitTester(c)
  } should be (true)
}
