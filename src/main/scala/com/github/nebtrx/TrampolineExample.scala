// Copyright 2017 Omar Ahmed
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.github.nebtrx

import scalaz.Free.Trampoline
import scalaz.Trampoline

object TrampolineExample extends App {

  println(recursiveFunction((1 to 3000000).toList).run)

  def recursiveFunction(l: List[Int]): Trampoline[List[Int]] = {
    if (l.isEmpty)
      Trampoline.done(Nil)
    else {
      Trampoline.suspend(recursiveFunction(l.tail)).flatMap { list =>
        Trampoline.done(l.head + 1 :: list)
      }
    }
  }
}

object NoTrampolineExample extends App {

  println(recursiveFunction((1 to 3000000).toList))

  def recursiveFunction(l: List[Int]): List[Int] = {
    if (l.isEmpty) Nil
    else l.head + 1 :: recursiveFunction(l.tail)
  }
}
