// See LICENSE for license details.
package fpga

import chisel3.iotesters
import gcd.GCD

/**
  * This provides a way to ruin the firrtl-interpreter REPL (or shell)
  * on the lowered firrtl generated by your circuit. You will be placed
  * in an interactive shell. This can be very helpful as a debugging
  * technique. Type help to see a list of commands.
  */
object MemoryMappedRepl extends App {
  iotesters.Driver.executeFirrtlRepl(args, () => new MemoryMappedDevice(new GCD, 32))
}
