package cn.qiuxiang.react.amap3d.maps

import android.view.View
import cn.qiuxiang.react.amap3d.toLatLng
import com.amap.api.maps.model.LatLng
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.common.MapBuilder
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager
import com.facebook.react.uimanager.annotations.ReactProp

@Suppress("unused")
internal class AMapMarkerManager : ViewGroupManager<AMapMarker>() {
    override fun getName(): String {
        return "AMapMarker"
    }

    override fun createViewInstance(reactContext: ThemedReactContext): AMapMarker {
        return AMapMarker(reactContext)
    }

    override fun addView(marker: AMapMarker, view: View, index: Int) {
        when (view) {
            is AMapInfoWindow -> marker.infoWindow = view
            else -> super.addView(marker, view, index)
        }
    }

    override fun getExportedCustomDirectEventTypeConstants(): Map<String, Any>? {
        return MapBuilder.of(
                "onPress", MapBuilder.of("registrationName", "onPress"),
                "onDragStart", MapBuilder.of("registrationName", "onDragStart"),
                "onDrag", MapBuilder.of("registrationName", "onDrag"),
                "onDragEnd", MapBuilder.of("registrationName", "onDragEnd"),
                "onInfoWindowPress", MapBuilder.of("registrationName", "onCalloutPress")
        )
    }

    companion object {
        val UPDATE = 1
        val ACTIVE = 2
        val LOCK_TO_SCREEN = 3
        val SHOW_CALLOUT = 4
        val HIDE_CALLOUT = 5
    }

    override fun getCommandsMap(): Map<String, Int> {
        return mapOf(
                "update" to UPDATE,
                "active" to ACTIVE,
                "lockToScreen" to LOCK_TO_SCREEN,
                "showCallout" to SHOW_CALLOUT,
                "hideCallout" to HIDE_CALLOUT
        )
    }

    override fun receiveCommand(marker: AMapMarker, commandId: Int, args: ReadableArray?) {
        when (commandId) {
            UPDATE -> marker.updateIcon()
            ACTIVE -> marker.active = true
            LOCK_TO_SCREEN -> marker.lockToScreen(args)
            SHOW_CALLOUT -> marker.active = true;
            HIDE_CALLOUT -> marker.active = false;
        }
    }

    @ReactProp(name = "title")
    fun setTitle(marker: AMapMarker, title: String?) {
        if (title != null) {
            marker.title = title;
            marker.active = true
        } else {
            marker.active = false;
        }
    }

    @ReactProp(name = "description")
    fun setSnippet(marker: AMapMarker, description: String?) {
        if (description != null) {
            marker.snippet = description;
        } else {
            marker.snippet = "";
        }
    }

    @ReactProp(name = "coordinate")
    fun setCoordinate(view: AMapMarker, coordinate: ReadableMap) {
        view.position = coordinate.toLatLng()
    }

    @ReactProp(name = "flat")
    fun setFlat(marker: AMapMarker, flat: Boolean) {
        marker.flat = flat
    }

    @ReactProp(name = "opacity")
    override fun setOpacity(marker: AMapMarker, opacity: Float) {
        marker.opacity = opacity
    }

    @ReactProp(name = "draggable")
    fun setDraggable(marker: AMapMarker, draggable: Boolean) {
        marker.draggable = draggable
    }

    @ReactProp(name = "clickDisabled")
    fun setClickDisabled(marker: AMapMarker, disabled: Boolean) {
        marker.clickDisabled = disabled
    }

    @ReactProp(name = "infoWindowDisabled")
    fun setInfoWindowDisabled(marker: AMapMarker, disabled: Boolean) {
        marker.infoWindowDisabled = disabled
    }

    @ReactProp(name = "active")
    fun setSelected(marker: AMapMarker, active: Boolean) {
        marker.active = active
    }

    @ReactProp(name = "pinColor")
    fun setIcon(marker: AMapMarker, icon: String) {
        marker.setIconColor(icon)
    }

    @ReactProp(name = "image")
    fun setImage(marker: AMapMarker, image: String) {
        marker.setImage(image)
    }

    @ReactProp(name = "zIndex")
    fun setZIndez(marker: AMapMarker, zIndex: Float) {
        marker.zIndex = zIndex
    }

    @ReactProp(name = "anchor")
    fun setAnchor(view: AMapMarker, coordinate: ReadableMap) {
        view.setAnchor(coordinate.getDouble("x"), coordinate.getDouble("y"))
    }

    @ReactProp(name = "identifier")
    fun setIdentifier(view: AMapMarker, identifier: String ) {
        view.identifier = identifier;
    }
}
