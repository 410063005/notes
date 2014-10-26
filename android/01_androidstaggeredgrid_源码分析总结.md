[category]: android
[keywords]: android,view
[source]: -
[date]: 2014-10-26

StaggeredGridView.onColumnSync 不容易理解，什么是 mSyncPosition？

	/***
	 * Our mColumnTops and mColumnBottoms need to be re-built up to the
	 * mSyncPosition - the following layout request will then
	 * layout the that position and then fillUp and fillDown appropriately.
	 */
	private void onColumnSync() {

	}


AbsListView 中关于 mSyncPosition 的注释

		// line 5471
                case SYNC_SELECTED_POSITION:
                    if (isInTouchMode()) {
                        // We saved our state when not in touch mode. (We know this because
                        // mSyncMode is SYNC_SELECTED_POSITION.) Now we are trying to
                        // restore in touch mode. Just leave mSyncPosition as it is (possibly
                        // adjusting if the available range changed) and return.
                        mLayoutMode = LAYOUT_SYNC;
                        mSyncPosition = Math.min(Math.max(0, mSyncPosition), count - 1);

                        return;

                case SYNC_FIRST_POSITION:
                    // Leave mSyncPosition as it is -- just pin to available range
                    mLayoutMode = LAYOUT_SYNC;
                    mSyncPosition = Math.min(Math.max(0, mSyncPosition), count - 1);

                    return;
                }

AdapterView 中关于 mSyncPosition 的注释

    /**
     * Position from which to start looking for mSyncRowId
     */
    int mSyncPosition;

AdapterView 中还有一个名为 int findSyncPosition() 方法
