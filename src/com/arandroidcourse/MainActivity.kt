/**
 * Copyright (C) 2014 Gianni Rosa Gallina.
 *
 * ViewWorldSample is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ViewWorldSample is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ViewWorldSample. If not, see <http:></http:>//www.gnu.org/licenses/>.
 */
package com.arandroidcourse

import android.app.Activity
import android.hardware.Camera
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import java.lang.Exception

class MainActivity : Activity() {
    private var m_Camera: Camera? = null
    private var m_Preview: PreviewSurface? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main);
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id: Int = item.getItemId()
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()
        if (m_Camera != null) {
            m_Camera!!.stopPreview()
            m_Camera!!.release()
            m_Camera = null
        }
    }

    override fun onResume() {
        super.onResume()
        m_Camera = cameraInstance
        initializeCameraParameters()
        if (m_Camera == null) {
            // setContentView(R.layout.activity_main);
        } else {
            m_Preview = PreviewSurface(this, m_Camera!!)
            setContentView(m_Preview)
        }
    }

    private fun initializeCameraParameters() {
        val parameters: Camera.Parameters = m_Camera?.getParameters()!!
        val sizes: List<Camera.Size> = parameters.getSupportedPreviewSizes()
        val size: Camera.Size? = getBestPreviewSize(640, 480, parameters)
        var currentWidth = 0
        var currentHeight = 0
        var foundDesiredWidth = false
        for (s in sizes) {
            if (size != null) {
                if (s.width === size.width) {
                    currentWidth = s.width
                    currentHeight = s.height
                    foundDesiredWidth = true
                    break
                }
            }
        }
        if (foundDesiredWidth) {
            parameters.setPreviewSize(currentWidth, currentHeight)
        }
        m_Camera?.setParameters(parameters)
    }

    private fun getBestPreviewSize(width: Int, height: Int, parameters: Camera.Parameters): Camera.Size? {
        var result: Camera.Size? = null
        for (size in parameters.getSupportedPreviewSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size
                } else {
                    val resultArea: Int = result.width * result.height
                    val newArea: Int = size.width * size.height
                    if (newArea > resultArea) {
                        result = size
                    }
                }
            }
        }
        return result
    }

    companion object {
        val cameraInstance: Camera?
            get() {
                var c: Camera? = null
                try {
                    c = Camera.open(0)
                } catch (e: Exception) {
                }
                return c
            }
    }
}