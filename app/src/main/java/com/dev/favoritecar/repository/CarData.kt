/*
 * Copyright (c) 2021 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.monsters.repository

import android.content.Context
import com.dev.favoritecar.R
import com.dev.favoritecar.model.Car


object CarData {
  fun getListOfCars(context: Context) : List<Car> = listOf(

      Car(
          name = context.getString(R.string.kia),
          picture = R.drawable.kia_1,
          sku = null,
          strength = 100
      ),
      Car(
          name = context.getString(R.string.huyndai),
          picture = R.drawable.huyndai_2,
          sku = null,
          strength = 200
      ),
      Car(
          name = context.getString(R.string.mazda),
          picture = R.drawable.mazda_3,
          sku = null,
          strength = 300
      ),
      Car(
          name = context.getString(R.string.mec),
          picture = R.drawable.mec_4,
          sku = null,
          strength = 400
      ),
      Car(
          name = context.getString(R.string.ford),
          picture = R.drawable.ford_5,
          sku = null,
          strength = 500
      ),
      Car(
          name = context.getString(R.string.lamborghini),
          picture = R.drawable.lamborghini_6,
          sku = null,
          strength = 600
      )
  )
}