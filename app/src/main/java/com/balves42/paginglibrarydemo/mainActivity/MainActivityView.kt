package com.balves42.paginglibrarydemo.mainActivity

import com.balves42.paginglibrarydemo.mainActivity.utils.State
import io.reactivex.functions.Action


interface MainActivityView {

    /**
     * Check if list is currently invalidated
     *
     * @return true / false accordingly
     */
    fun listIsInvalidated(): Boolean

    /**
     * Check if list is currently empty
     *
     * @return true / false accordingly
     */
    fun listIsEmpty(): Boolean

    /**
     * Se the state of the adapter
     *
     * @param state state to be set
     */
    fun setAdapterState(state: State)

    /**
     * Set the visibility of the list
     *
     * @param show true / false accordingly
     */
    fun setListVisibility(show: Boolean)

    /**
     * Set the visibility of the error message
     *
     * @param show true / false accordingly
     */
    fun setErrorVisibility(show: Boolean)

    /**
     * Update the state live data
     *
     * @param state new state
     */
    fun updateState(state: State)

    /**
     * Set the visibility of the
     *
     * @param show true / false accordingly
     */
    fun setPBVisibility(show: Boolean)

    /**
     * Invalidate the list
     *
     */
    fun invalidateList()

    /**
     * Retry behaviour
     *
     */
    fun retry()

    /**
     * Set the retry information
     *
     * @param action to be performed
     */
    fun setRetry(action: Action?)
}