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


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.favoritecar.carstore.CarStoreFragment
import com.dev.favoritecar.databinding.CarItemBinding
import com.dev.favoritecar.model.Car
import com.raywenderlich.android.monsters.repository.CarData

class CarsAdapter(
  private val carStoreFragment: CarStoreFragment,
  val onItemClickListener: (Car) -> (Unit)
) : RecyclerView.Adapter<CarsAdapter.ViewHolder>() {

  private val cars: List<Car?> by lazy {
    CarData.getListOfCars(carStoreFragment.requireContext())
  }

  inner class ViewHolder(private val binding: CarItemBinding)
    : RecyclerView.ViewHolder(binding.root) {
    fun bind(car: Car) {
      binding.apply {
        this.car = car
        container.setOnClickListener {
          onItemClickListener(car)
          Log.d("onItemClickListener", "onItemClickListener: " + car.name)
        }
      }
//      carStoreFragment.getSkuPrice(car.sku)?.
//      observe(carStoreFragment.viewLifecycleOwner) {
//        binding.skuPrice.apply {
//          text = it
//          visibility = View.VISIBLE
//        }
//      }


      // TODO: observe getSkuPrice and set the price for every monster

      binding.executePendingBindings()
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    val binding = CarItemBinding.inflate(layoutInflater, parent, false)
    return ViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    if (null != cars[position]) {
      holder.bind(cars[position]!!)
    }
  }

  override fun getItemCount() = cars.size
}