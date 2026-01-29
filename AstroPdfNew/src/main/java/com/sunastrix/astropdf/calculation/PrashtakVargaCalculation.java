package com.sunastrix.astropdf.calculation;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sunastrix.astroganitlib.horo.DesktopHoroNew;
import com.sunastrix.astropdf.model.PrastharashtakvargaModel;


public class PrashtakVargaCalculation {
	DesktopHoroNew desktopHoro;

	public PrashtakVargaCalculation(DesktopHoroNew desktopHoro) {
		this.desktopHoro = desktopHoro;
	}

	public ArrayList<PrastharashtakvargaModel> getPrashtakVargaData() {
		JSONArray prastharashtakvargaJsonArray = new JSONArray();
		String[] bindu = { "SU", "MO", "MA", "ME", "JU", "VE", "SA", "AS" };
		String[] plaNo = { "SUN", "MOON", "MARS", "MERCURY", "JUPITER", "VENUS", "SATURN", "RAHU" };
		for (int i = 1; i <= 8; i++) {
			JSONObject jsonObject1 = new JSONObject();
			for (int j = 1; j <= bindu.length; j++) {
				StringBuilder str = new StringBuilder();
				for (int k = 1; k < 13; k++) {
					str.append(desktopHoro.getPrastharashtakvargaTables(i, j, k)).append(",");
				}
				jsonObject1.put(bindu[j - 1], str);
			}
			prastharashtakvargaJsonArray.put(jsonObject1);
		}

		return parseData(prastharashtakvargaJsonArray);
	}

	public ArrayList<PrastharashtakvargaModel> parseData(JSONArray jsonArray) {
		ArrayList<PrastharashtakvargaModel> prastharashtakvargaBeanList = new ArrayList<PrastharashtakvargaModel>();
		PrastharashtakvargaModel prastharashtakvargaModel;
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			prastharashtakvargaModel = new PrastharashtakvargaModel();
			prastharashtakvargaModel.setSu(jsonObject.get("SU").toString());
			prastharashtakvargaModel.setMo(jsonObject.get("MO").toString());
			prastharashtakvargaModel.setMa(jsonObject.get("MA").toString());
			prastharashtakvargaModel.setMe(jsonObject.get("ME").toString());
			prastharashtakvargaModel.setJu(jsonObject.get("JU").toString());
			prastharashtakvargaModel.setVe(jsonObject.get("VE").toString());
			prastharashtakvargaModel.setSa(jsonObject.get("SA").toString());
			prastharashtakvargaModel.setAsc(jsonObject.get("AS").toString());
			prastharashtakvargaBeanList.add(prastharashtakvargaModel);
		}
		return prastharashtakvargaBeanList;
	}

	private String getJsonStringVal(JSONObject jsonObject, String key) {
		return jsonObject.getString(key);
	}

}
