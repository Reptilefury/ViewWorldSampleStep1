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

import android.content.Context
import android.hardware.Camera
import android.view.SurfaceHolder
import android.view.SurfaceView

class PreviewSurface(context: Context?, camera: Camera) : SurfaceView(context),
    SurfaceHolder.Callback {
    private val m_Holder: SurfaceHolder
    private val m_Camera: Camera
    override fun surfaceCreated(holder: SurfaceHolder?) {
        // TODO Auto-generated method stub
    }

    override fun surfaceChanged(
        holder: SurfaceHolder?, format: Int, width: Int,
        height: Int
    ) {
        if (m_Holder.getSurface() == null) return
        try {
            m_Camera.stopPreview()
        } catch (e: Exception) {
            // Ignore
        }
        try {
            m_Camera.setPreviewDisplay(m_Holder)
            m_Camera.startPreview()
        } catch (e: Exception) {
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        // TODO Auto-generated method stub
    }

    init {
        m_Camera = camera
        m_Holder = getHolder()
        m_Holder.addCallback(this)
    }
}