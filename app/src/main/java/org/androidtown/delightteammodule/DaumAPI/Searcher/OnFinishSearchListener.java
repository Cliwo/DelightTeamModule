package org.androidtown.delightteammodule.DaumAPI.Searcher;

import java.util.List;

public interface OnFinishSearchListener {
	public void onSuccess(List<Item> itemList);
	public void onFail();
}
