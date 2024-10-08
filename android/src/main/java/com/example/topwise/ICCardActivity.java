package com.example.topwise;

import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.topwise.cloudpos.aidl.iccard.AidlICCard;

import java.util.HashMap;
import java.util.Map;

/**
 *  Chip card test
 * 
 * @author Administrator
 * 
 */
public class ICCardActivity extends BaseUtils{

	private AidlICCard iccard = null;
	String data = "";
	private static final String icCard= "icCard";



	public ICCardActivity(AidlICCard aidlICCard) {
		this.iccard = aidlICCard;
	}

	public interface ICCardCallback {
		void onEventFinish(String value);
	}

	/**
	 * open ic card
	 */
	public void open(ICCardCallback callback) {
		if(isNormalVelocityClick(DELAY_TIME)) {
			try {
				boolean flag = iccard.open();
				if (flag) {
					data = "IC Card Open Success";
					new Handler(Looper.getMainLooper()).post(new Runnable() {
						@Override
						public void run() {
							if (callback != null) {
								callback.onEventFinish(data);
							}
						}
					});
				} else {
					data = "IC Card Open Failure";
					new Handler(Looper.getMainLooper()).post(new Runnable() {
						@Override
						public void run() {
							if (callback != null) {
								callback.onEventFinish(data);
							}
						}
					});
				}
			} catch (RemoteException e) {
				e.printStackTrace();
				data = e.toString();
				new Handler(Looper.getMainLooper()).post(new Runnable() {
					@Override
					public void run() {
						if (callback != null) {
							callback.onEventFinish(data);
						}
					}
				});
			}
		} else {
			data = "Do Not Click Quickly !";
			new Handler(Looper.getMainLooper()).post(new Runnable() {
				@Override
				public void run() {
					if (callback != null) {
						callback.onEventFinish(data);
					}
				}
			});
		}
	}

	/**
	 * close ic card
	 */
	public void close(ICCardCallback callback) {
		if(isNormalVelocityClick(DELAY_TIME)) {
			try {
				boolean flag = iccard.close();
				if (flag) {
					data = "IC Card Close Success";
					new Handler(Looper.getMainLooper()).post(new Runnable() {
						@Override
						public void run() {
							if (callback != null) {
								callback.onEventFinish(data);
							}
						}
					});
				} else {
					data = "IC Card Close Failure";
					new Handler(Looper.getMainLooper()).post(new Runnable() {
						@Override
						public void run() {
							if (callback != null) {
								callback.onEventFinish(data);
							}
						}
					});
				}
			} catch (RemoteException e) {
				e.printStackTrace();
				data = e.toString();
				new Handler(Looper.getMainLooper()).post(new Runnable() {
					@Override
					public void run() {
						if (callback != null) {
							callback.onEventFinish(data);
						}
					}
				});
			}
		} else {
			data = "Do Not Click Quickly !";
			new Handler(Looper.getMainLooper()).post(new Runnable() {
				@Override
				public void run() {
					if (callback != null) {
						callback.onEventFinish(data);
					}
				}
			});
		}
	}

	public void halt(ICCardCallback callback) {
		if(isNormalVelocityClick(DELAY_TIME)) {
			try {
				int ret = iccard.halt();
				if (ret == 0x00) {

					data = "IC Card Disconnect Success";
					new Handler(Looper.getMainLooper()).post(new Runnable() {
						@Override
						public void run() {
							if (callback != null) {
								callback.onEventFinish(data);
							}
						}
					});
				} else {

					data = "IC Card Disconnect Failure";
					new Handler(Looper.getMainLooper()).post(new Runnable() {
						@Override
						public void run() {
							if (callback != null) {
								callback.onEventFinish(data);
							}
						}
					});
				}
			} catch (RemoteException e) {
				e.printStackTrace();
				data = e.toString();
				new Handler(Looper.getMainLooper()).post(new Runnable() {
					@Override
					public void run() {
						if (callback != null) {
							callback.onEventFinish(data);
						}
					}
				});
			}
		} else {
			data = "Do Not Click Quickly !";
			new Handler(Looper.getMainLooper()).post(new Runnable() {
				@Override
				public void run() {
					if (callback != null) {
						callback.onEventFinish(data);
					}
				}
			});
		}
	}


	public void apduComm(ICCardCallback callback) {
		if(isNormalVelocityClick(DELAY_TIME)) {
			byte[] apdu = HexUtil
					.hexStringToByte("0000000000");
			try {
				byte[] dataInternal = iccard.apduComm(apdu);
				if (null != dataInternal) {

					data = "Choice Main Menu Result"+ HexUtil.bcd2str(dataInternal);
					new Handler(Looper.getMainLooper()).post(new Runnable() {
						@Override
						public void run() {
							if (callback != null) {
								callback.onEventFinish(data);
							}
						}
					});

				} else {

					data = "Test APDU data failed";
					new Handler(Looper.getMainLooper()).post(new Runnable() {
						@Override
						public void run() {
							if (callback != null) {
								callback.onEventFinish(data);
							}
						}
					});
				}
			} catch (RemoteException e) {
				e.printStackTrace();
				data = e.toString();
				new Handler(Looper.getMainLooper()).post(new Runnable() {
					@Override
					public void run() {
						if (callback != null) {
							callback.onEventFinish(data);
						}
					}
				});
			}
		} else {
			data = "Do Not Click Quickly !";
			new Handler(Looper.getMainLooper()).post(new Runnable() {
				@Override
				public void run() {
					if (callback != null) {
						callback.onEventFinish(data);
					}
				}
			});
		}
	}

	public void apduCommCustom(ICCardCallback callback, String apduCustom) {
		if(isNormalVelocityClick(DELAY_TIME)) {
			byte[] apdu = HexUtil
					.hexStringToByte(apduCustom);
			try {
				byte[] dataInternal = iccard.apduComm(apdu);
				if (null != dataInternal) {

					data = "Custom-Apdu-Result = "+ HexUtil.bcd2str(dataInternal);
					new Handler(Looper.getMainLooper()).post(new Runnable() {
						@Override
						public void run() {
							if (callback != null) {
								callback.onEventFinish(data);
							}
						}
					});

				} else {

					data = "Test APDU data failed";
					new Handler(Looper.getMainLooper()).post(new Runnable() {
						@Override
						public void run() {
							if (callback != null) {
								callback.onEventFinish(data);
							}
						}
					});
				}
			} catch (RemoteException e) {
				e.printStackTrace();
				data = e.toString();
				new Handler(Looper.getMainLooper()).post(new Runnable() {
					@Override
					public void run() {
						if (callback != null) {
							callback.onEventFinish(data);
						}
					}
				});
			}
		} else {
			data = "Do Not Click Quickly !";
			new Handler(Looper.getMainLooper()).post(new Runnable() {
				@Override
				public void run() {
					if (callback != null) {
						callback.onEventFinish(data);
					}
				}
			});
		}
	}

	public void cardReset(ICCardCallback callback) {
		if(isNormalVelocityClick(DELAY_TIME)) {
			try {
				byte[] dataInternal = iccard.reset(0x00);
				if (null != dataInternal && dataInternal.length != 0) {

					data = "IC Card Reset "+HexUtil.bcd2str(dataInternal);
					new Handler(Looper.getMainLooper()).post(new Runnable() {
						@Override
						public void run() {
							if (callback != null) {
								callback.onEventFinish(data);
							}
						}
					});
				} else {
					data = "IC Card Reset Failure";
					new Handler(Looper.getMainLooper()).post(new Runnable() {
						@Override
						public void run() {
							if (callback != null) {
								callback.onEventFinish(data);
							}
						}
					});
				}
			} catch (RemoteException e) {
				e.printStackTrace();
				data = e.toString();
				new Handler(Looper.getMainLooper()).post(new Runnable() {
					@Override
					public void run() {
						if (callback != null) {
							callback.onEventFinish(data);
						}
					}
				});
			}
		} else {
			data = "Do Not Click Quickly !";
			new Handler(Looper.getMainLooper()).post(new Runnable() {
				@Override
				public void run() {
					if (callback != null) {
						callback.onEventFinish(data);
					}
				}
			});
		}
	}

	public void isExists(ICCardCallback callback) {//检卡有事会失败，复位后再次检测
		if(isNormalVelocityClick(DELAY_TIME)) {
			try {
				boolean flag = iccard.isExist();
				if (flag) {

					Map<String, Object> dataMap = new HashMap<>();

					dataMap.put("isCardExist", true);

					ObjectMapper objectMapper = new ObjectMapper();

					data = objectMapper.writeValueAsString(dataMap);

					new Handler(Looper.getMainLooper()).post(new Runnable() {
						@Override
						public void run() {
							if (callback != null) {
								callback.onEventFinish(data);
							}
						}
					});

				} else {
					Map<String, Object> dataMap = new HashMap<>();

					dataMap.put("isCardExist", false);

					ObjectMapper objectMapper = new ObjectMapper();

					data = objectMapper.writeValueAsString(dataMap);

					new Handler(Looper.getMainLooper()).post(new Runnable() {
						@Override
						public void run() {
							if (callback != null) {
								callback.onEventFinish(data);
							}
						}
					});
				}
			} catch (RemoteException e) {
				e.printStackTrace();
				data = e.toString();
				new Handler(Looper.getMainLooper()).post(new Runnable() {
					@Override
					public void run() {
						if (callback != null) {
							callback.onEventFinish(data);
						}
					}
				});
			} catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else {
			data = "Do Not Click Quickly !";
			new Handler(Looper.getMainLooper()).post(new Runnable() {
				@Override
				public void run() {
					if (callback != null) {
						callback.onEventFinish(data);
					}
				}
			});
		}
	}


}
