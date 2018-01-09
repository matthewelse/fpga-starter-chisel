package fpga

import chisel3._

class CppField(val name: String, val typeName: String, val offset: Int, val element: Data)
