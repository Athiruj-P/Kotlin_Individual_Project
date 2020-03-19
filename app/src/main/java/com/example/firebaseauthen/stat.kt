package com.example.firebaseauthen


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate


/**
 * A simple [Fragment] subclass.
 */
class stat : Fragment() {
    lateinit var Bar_id: BarChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stat, container, false)
        Bar_id = view.findViewById(R.id.Bar_id)
        Bar_Chart(Bar_id)
        return view
    }

    fun Bar_Chart(chart: BarChart) {

        chart.description.isEnabled = false
        val listGross: ArrayList<Float> = ArrayList()
        listGross.add(858f)
        listGross.add(543f)
        listGross.add(451f)
        listGross.add(450f)
        listGross.add(434f)
        listGross.add(426f)
        listGross.add(390f)
        listGross.add(355f)
        listGross.add(333f)
        listGross.add(235f)

        val listRank: ArrayList<String> = ArrayList()
        listRank.add("A")
        listRank.add("B")
        listRank.add("C")
        listRank.add("D")
        listRank.add("E")
        listRank.add("F")
        listRank.add("G")
        listRank.add("H")
        listRank.add("I")
        listRank.add("J")

        val entries: ArrayList<BarEntry> = ArrayList()
        var index: Float = 0F
        for (gross in listGross) {
            entries.add(BarEntry(index, gross))
            index++
        }

        val dataset = BarDataSet(entries, "Rank")
        dataset.valueTextSize = 10F
        dataset.setColors(*ColorTemplate.COLORFUL_COLORS)

        val dataSets = ArrayList<IBarDataSet>()
        dataSets.add(dataset)

        val data = BarData(dataSets)
        chart.setData(data)
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM)

        val xAxis = chart.xAxis
        xAxis.setTextSize(10F)
        xAxis.labelCount = entries.size
        xAxis.granularity = 1f
        xAxis.valueFormatter = IndexAxisValueFormatter(listRank)

        val leftAxis = chart.axisLeft
        leftAxis.setLabelCount(8, false)
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.spaceTop = 15f
        leftAxis.axisMinimum = 0f

        val rightAxis = chart.axisRight
        rightAxis.setEnabled(false)

        chart.animateY(2000)
        chart.animateX(2000)

    }
}
