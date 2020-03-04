package mobileUtilities;

import java.math.BigDecimal;
import java.math.RoundingMode;

import Libraries.Result;

public class Charging {

	public String LocalSMSCharging() {
		double pre, post, charging, actual;
		String Test_OutPut = "", Status = "", Bucket, UserBucket;
		UserBucket = utils.fetchData("BucketID");
		try {
			if (UserBucket != null) {
				Result.fUpdateLog(UserBucket + " Bucket is given by user hence will verify consumtion of this bucket.");
				Test_OutPut += UserBucket + " Bucket is given by user hence will verify consumtion of this bucket.";
				pre = utils.balance(UserBucket, "pre");
				Result.fUpdateLog(pre + " is the previous balance ");
				Test_OutPut += pre + " is the previous balance\n";
				post = utils.balance(UserBucket, "post");
				Result.fUpdateLog(post + " is the balance after usage");
				Test_OutPut += post + " is the balance after usage.\n";
				actual = round(pre - post, 2);
				Result.fUpdateLog(actual + " is the actual charging happened.");
				Test_OutPut += actual + " is the actual charging happened.\n";
				charging = utils.getCharging(UserBucket, "LocalSMS");
				Result.fUpdateLog(charging + " is the charging expected.");
				Test_OutPut += charging + " is the charging expected.\n";
				if (actual == charging) {
					Status = "PASS";
					System.out.println("Pass");
				} else {
					Status = "FAIL";
					System.out.println("Fail");
				}
			} else {
				Bucket = utils.findpriority("LocalCall");
				Result.fUpdateLog(Bucket + " Bucket is having high priority, hence will consume first. ");
				Test_OutPut += Bucket + " Bucket is having high priority, hence will consume first.\n";
				pre = utils.balance(Bucket, "pre");
				Result.fUpdateLog(pre + " is the previous balance ");
				Test_OutPut += pre + " is the previous balance\n";
				post = utils.balance(Bucket, "post");
				Result.fUpdateLog(post + " is the balance after usage");
				Test_OutPut += post + " is the balance after usage.\n";
				actual = round(pre - post, 2);
				Result.fUpdateLog(actual + " is the actual charging happened.");
				Test_OutPut += actual + " is the actual charging happened.\n";
				charging = utils.getCharging(Bucket, "LocalSMS");
				Result.fUpdateLog(charging + " is the charging expected.");
				Test_OutPut += charging + " is the charging expected.\n";
				if (actual == charging) {
					Status = "PASS";
					System.out.println("Pass");
				} else {
					Status = "FAIL";
					System.out.println("Fail");
				}
			}
		} catch (Exception e) {
			Status = "FAIL";
			Test_OutPut += "Exception Occured" + e;
		}
		return Status + "@@" + Test_OutPut + "<br/>";

	}

	public String LocalCallCharging() {
		Result.fUpdateLog("------Local call Charging -----");
		double pre, post, charging, actual;
		String Test_OutPut = "", Status = "", Bucket, UserBucket;
		UserBucket = utils.fetchData("BucketID");
		try {
			if (!(UserBucket == null)) {
				Result.fUpdateLog(UserBucket + " Bucket is given by user hence will verify consumtion of this bucket.");
				Test_OutPut += UserBucket + " Bucket is given by user hence will verify consumtion of this bucket.";
				pre = utils.balance(UserBucket, "pre");
				Result.fUpdateLog(pre + " is the previous balance ");
				Test_OutPut += pre + " is the previous balance\n";
				post = utils.balance(UserBucket, "post");
				Result.fUpdateLog(post + " is the balance after usage");
				Test_OutPut += post + " is the balance after usage.\n";
				actual = round(pre - post, 2);
				Result.fUpdateLog(actual + " is the actual charging happened.");
				Test_OutPut += actual + " is the actual charging happened.\n";
				try {
					charging = utils.getCharging(UserBucket, "LocalCall");
					Result.fUpdateLog(charging + " is the charging expected.");
					Test_OutPut += charging + " is the charging expected.\n";
					if (actual == charging) {
						Status = "PASS";
						System.out.println("Pass");
					} else {
						Status = "FAIL";
						System.out.println("Fail");
					}
				} catch (Exception e) {
					System.out.println("User Bucket not found in RTB");
				}
			} else {
				Bucket = utils.findpriority("LocalCall");
				Result.fUpdateLog(Bucket + "Bucket is having high priority, hence will consume first. ");
				Test_OutPut += Bucket + " Bucket is having high priority, hence will consume first.\n";
				pre = utils.balance(Bucket, "pre");
				Result.fUpdateLog(pre + " is the previous balance ");
				Test_OutPut += pre + " is the previous balance\n";
				post = utils.balance(Bucket, "post");
				Result.fUpdateLog(post + " is the balance after usage");
				Test_OutPut += post + " is the balance after usage.\n";
				actual = round(pre - post, 2);
				Result.fUpdateLog(actual + " is the actual charging happened.");
				Test_OutPut += actual + " is the actual charging happened.\n";
				charging = utils.getCharging(Bucket, "LocalCall");
				Result.fUpdateLog(charging + " is the charging expected.");
				Test_OutPut += charging + " is the charging expected.\n";
				if (actual == charging) {
					Status = "PASS";
					System.out.println("Pass");
				} else {
					Status = "FAIL";
					System.out.println("Fail");
				}
			}
		} catch (Exception e) {
			Status = "FAIL";
			Test_OutPut += "Exception Occured" + e;
		}
		return Status + "@@" + Test_OutPut + "<br/>";
	}

	public String InternationalCallCharging() {
		double pre, post, charging, actual;
		String Test_OutPut = "", Status = "", Bucket, UserBucket, Country;
		Country = utils.fetchData("Country");
		UserBucket = utils.fetchData("BucketID");

		try {
			if (UserBucket != null) {
				Result.fUpdateLog(
						UserBucket + " Bucket is given by user hence will verify consumtion of this bucket. ");
				Test_OutPut += UserBucket + "Bucket is given by user hence will verify consumtion of this bucket. ";
				pre = utils.balance(UserBucket, "pre");
				Result.fUpdateLog(pre + " is the previous balance ");
				Test_OutPut += pre + " is the previous balance\n";
				post = utils.balance(UserBucket, "post");
				Result.fUpdateLog(post + " is the balance after usage");
				Test_OutPut += post + " is the balance after usage.\n";
				actual = round(pre - post, 2);
				Result.fUpdateLog(actual + " is the actual charging happened.");
				Test_OutPut += actual + " is the actual charging happened.\n";
				charging = utils.getInternationalCallCharging(UserBucket, Country);
				Result.fUpdateLog(charging + " is the charging expected.");
				Test_OutPut += charging + " is the charging expected.\n";
				if (actual == charging) {
					Status = "PASS";
					System.out.println("Pass");
				} else {
					Status = "FAIL";
					System.out.println("Fail");
				}
			} else {
				Bucket = utils.findpriority("InternationalCall");
				Result.fUpdateLog(Bucket + " Bucket is having high priority, hence will consume first. ");
				Test_OutPut += Bucket + " Bucket is having high priority, hence will consume first.\n";
				pre = utils.balance(Bucket, "pre");
				Result.fUpdateLog(pre + " is the previous balance ");
				Test_OutPut += pre + " is the previous balance\n";
				post = utils.balance(Bucket, "post");
				Result.fUpdateLog(post + " is the balance after usage");
				Test_OutPut += post + " is the balance after usage.\n";
				actual = round(pre - post, 2);
				Result.fUpdateLog(actual + " is the actual charging happened.");
				Test_OutPut += actual + " is the actual charging happened.\n";
				charging = utils.getInternationalCallCharging(Bucket, Country);
				Result.fUpdateLog(charging + " is the charging expected.");
				Test_OutPut += charging + " is the charging expected.\n";
				if (actual == charging) {
					Status = "PASS";
					System.out.println("Pass");
				} else {
					Status = "FAIL";
					System.out.println("Fail");
				}
			}
		} catch (Exception e) {
			Status = "FAIL";
			Test_OutPut += "Exception Occured" + e;
		}
		return Status + "@@" + Test_OutPut + "<br/>";

	}

	public String InternationalSMSCharging() {
		double pre, post, charging, actual;
		String Test_OutPut = "", Status = "", Bucket, UserBucket, Country;
		Country = utils.fetchData("Country");
		UserBucket = utils.fetchData("BucketID");

		try {
			if (UserBucket != null) {
				Result.fUpdateLog(
						UserBucket + " Bucket is given by user hence will verify consumtion of this bucket. ");
				Test_OutPut += UserBucket + "Bucket is given by user hence will verify consumtion of this bucket. ";
				pre = utils.balance(UserBucket, "pre");
				Result.fUpdateLog(pre + " is the previous balance ");
				Test_OutPut += pre + " is the previous balance\n";
				post = utils.balance(UserBucket, "post");
				Result.fUpdateLog(post + " is the balance after usage");
				Test_OutPut += post + " is the balance after usage.\n";
				actual = round(pre - post, 2);
				Result.fUpdateLog(actual + " is the actual charging happened.");
				Test_OutPut += actual + " is the actual charging happened.\n";
				charging = utils.getInternationalSMSCharging(UserBucket, Country);
				Result.fUpdateLog(charging + " is the charging expected.");
				Test_OutPut += charging + " is the charging expected.\n";
				if (actual == charging) {
					Status = "PASS";
					System.out.println("Pass");
				} else {
					Status = "FAIL";
					System.out.println("Fail");
				}
			} else {
				Bucket = utils.findpriority("InternationalCall");
				Result.fUpdateLog(Bucket + " Bucket is having high priority, hence will consume first. ");
				Test_OutPut += Bucket + " Bucket is having high priority, hence will consume first.\n";
				pre = utils.balance(Bucket, "pre");
				Result.fUpdateLog(pre + " is the previous balance ");
				Test_OutPut += pre + " is the previous balance\n";
				post = utils.balance(Bucket, "post");
				Result.fUpdateLog(post + " is the balance after usage");
				Test_OutPut += post + " is the balance after usage.\n";
				actual = round(pre - post, 2);
				Result.fUpdateLog(actual + " is the actual charging happened.");
				Test_OutPut += actual + " is the actual charging happened.\n";
				charging = utils.getInternationalSMSCharging(Bucket, Country);
				Result.fUpdateLog(charging + " is the charging expected.");
				Test_OutPut += charging + " is the charging expected.\n";
				if (actual == charging) {
					Status = "PASS";
					System.out.println("Pass");
				} else {
					Status = "FAIL";
					System.out.println("Fail");
				}
			}
		} catch (Exception e) {
			Status = "FAIL";
			Test_OutPut += "Exception Occured" + e;
		}
		return Status + "@@" + Test_OutPut + "<br/>";

	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

}
