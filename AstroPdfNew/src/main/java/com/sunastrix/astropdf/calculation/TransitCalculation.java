package com.sunastrix.astropdf.calculation;

import java.util.Calendar;

import com.sunastrix.astroganitlib.horo.DesktopHoroNew;
import com.sunastrix.astroganitlib.model.BirthDetailBean;
import com.sunastrix.astroganitlib.model.DateTimeBean;
import com.sunastrix.astroganitlib.model.PlaceDetail;
import com.sunastrix.astropdf.model.TransitModel;

public class TransitCalculation {
	public TransitModel calculate(BirthDetailBean birthDetailBean) {

		BirthDetailBean birthDetailBeanNew = getBirthDetailForTransit(birthDetailBean);
		int lagna = getDesktopHoro(birthDetailBean).getPositionForShodasvarg(0)[0];

		int[] plaRashiArr = getIntArray(getDesktopHoro(birthDetailBeanNew).getPositionForShodasvarg(0), 0);
		plaRashiArr[plaRashiArr.length - 1] = lagna;
		TransitModel transitModel = new TransitModel();
		transitModel.setLagna(lagna);
		transitModel.setPlaRashiArray(plaRashiArr);

		return transitModel;
	}

	/*
	 * private fun getIntArrayFromString(planetPositionArr: IntArray, lagnaPos:
	 * Int): IntArray { val intArray = IntArray(13) val lagna =
	 * planetPositionArr[lagnaPos] for (i in 0 until planetPositionArr.size - 1) {
	 * intArray[i] = planetPositionArr[i + 1] } intArray[planetPositionArr.size - 1]
	 * = lagna return intArray }
	 */
	private int[] getIntArray(int[] planetPosition, int lagnaPos) {

		int[] intArray = new int[13];
		int lagna = planetPosition[lagnaPos];
		for (int i = 0; i < planetPosition.length - 1; i++) {
			intArray[i] = planetPosition[i + 1];
		}
		intArray[planetPosition.length - 1] = lagna;
		return intArray;
	}

	public DesktopHoroNew getDesktopHoro(BirthDetailBean birthDetailBean) {
		DesktopHoroNew args1 = new DesktopHoroNew();
		try {
			args1.setBirthDetail(birthDetailBean);
			args1.initialize();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return args1;
	}

	private BirthDetailBean getBirthDetailForTransit(BirthDetailBean birthDetailBean) {
		Calendar cal = Calendar.getInstance();

		PlaceDetail placeDetail = birthDetailBean.getPlaceDetail();
		DateTimeBean dateTimeBean = new DateTimeBean(String.valueOf(cal.get(Calendar.DATE)),
				String.valueOf(cal.get(Calendar.MONTH)), String.valueOf(cal.get(Calendar.YEAR)),
				String.valueOf(cal.get(Calendar.HOUR_OF_DAY)), String.valueOf(cal.get(Calendar.MINUTE)),
				String.valueOf(cal.get(Calendar.SECOND)));
		return new BirthDetailBean(birthDetailBean.getId(), birthDetailBean.getName(), birthDetailBean.getSex(),
				dateTimeBean, placeDetail, birthDetailBean.getDst(), birthDetailBean.getAyanamsa(), "",
				birthDetailBean.getKphn(), "", birthDetailBean.getLanguageCode(), -1L, -1L, -1L);

	}
}
