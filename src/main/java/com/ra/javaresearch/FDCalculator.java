package com.ra.javaresearch;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

class TimeUtil {
  public static int getDiffYears(Date first, Date last) {
    Calendar a = getCalendar(first);
    Calendar b = getCalendar(last);
    int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
    if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH)
        || (a.get(Calendar.MONTH) == b.get(Calendar.MONTH)
            && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
      diff--;
    }
    return diff;
  }

  public static Calendar getCalendar(Date date) {
    Calendar cal = Calendar.getInstance(TimeUtil.getIndiaTimeZone(), Locale.ENGLISH);
    cal.setTime(date);
    return cal;
  }

  public static ZoneId getIndiaZoneId() {
    return ZoneId.of("Asia/Kolkata");
  }

  public static TimeZone getIndiaTimeZone() {
    return TimeZone.getTimeZone(TimeUtil.getIndiaZoneId());
  }
}

enum InterestCompoundFrequency {
  MONTHLY(12),
  QUARTERLY(3),
  HALF_YEARLY(2),
  YEARLY(1);

  final int n;

  InterestCompoundFrequency(final int n) {
    this.n = n;
  }

  public int getFrequency() {
    return n;
  }
}

class Investment {
  final double principalAmt;
  final double interestRate;
  final InterestCompoundFrequency interestCompoundFrequency;
  final Date startDate;
  final Date maturityDate;

  Investment(
      double principalAmt,
      double interestRate,
      InterestCompoundFrequency interestCompoundFrequency,
      Date startDate,
      Date maturityDate) {
    this.principalAmt = principalAmt;
    this.interestRate = interestRate;
    this.interestCompoundFrequency = interestCompoundFrequency;
    this.startDate = startDate;
    this.maturityDate = maturityDate;
  }

  public boolean willMatureOnDate(final Date date) {
    return date.after(maturityDate);
  }

  public static double compoundInterest(
      final double p, final double r, final double n, final double t) {
    return p * Math.pow(1.0D + r / (100.0D * n), n * t);
  }

  public double getAmtTillDate(final Date date) {
    if (date.before(startDate)) {
      throw new UnsupportedOperationException("Invalid Date: Date cannot be in past!");
    }
    if (date.after(maturityDate)) {
      return getAmtTillDate(maturityDate);
    }
    return compoundInterest(
        principalAmt,
        interestRate,
        interestCompoundFrequency.getFrequency(),
        TimeUtil.getDiffYears(startDate, date));
  }

  public double getInterestGatheredTillDate(final Date date) {
    return getAmtTillDate(date) - principalAmt;
  }

  @Override
  public String toString() {
    return "Investment{" + "principalAmt=" + principalAmt + ", startDate=" + startDate + '}';
  }
}

public class FDCalculator {

  private static Date addToDate(TimeZone timeZone, Date initialDate, int i0, int i1) {
    final Calendar calendar = Calendar.getInstance();
    calendar.setTimeZone(timeZone);
    calendar.setTime(initialDate);
    calendar.add(i0, i1);
    return calendar.getTime();
  }

  public static void main(String[] args) throws ParseException {
    final List<Investment> investmentList = new ArrayList<>();

    final int startYear = 2029, startMonth = 1, startDay = 1; // change-here
    final double principalAmt = 50000.0D * 2; // change-here
    final double interestRate = 6.5D; // change-here
    final int investmentMaturityInYears = 10; // change-here
    final int periodOfInvestmentInYears = 10; // change-here
    final int periodOfInvestmentReportInYears = 10; // change-here

    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    final Date investmentStartDate =
        simpleDateFormat.parse(String.format("%s-%s-%s", startYear, startMonth, startDay));
    final Date lastInvestmentReportDate =
        addToDate(
            TimeUtil.getIndiaTimeZone(),
            investmentStartDate,
            Calendar.YEAR,
            periodOfInvestmentReportInYears);

    Date nextInvestmentDate = investmentStartDate;
    int j = startMonth;
    for (int year = startYear; year < startYear + periodOfInvestmentInYears; year++) {
      for (; j <= 12; j++) {

        investmentList.add(
            new Investment(
                principalAmt,
                interestRate,
                InterestCompoundFrequency.YEARLY,
                nextInvestmentDate,
                addToDate(
                    TimeUtil.getIndiaTimeZone(),
                    nextInvestmentDate,
                    Calendar.YEAR,
                    investmentMaturityInYears)));

        nextInvestmentDate =
            addToDate(
                TimeUtil.getIndiaTimeZone(),
                nextInvestmentDate,
                Calendar.DAY_OF_MONTH,
                YearMonth.of(year, j).lengthOfMonth());
      }
      j = 1;
    }

    BigDecimal netPrincipal = BigDecimal.ZERO;
    BigDecimal netAmountOnEndDate = BigDecimal.ZERO;
    for (Investment investment : investmentList) {
      if (investment.startDate.before(lastInvestmentReportDate)) {
        netPrincipal = netPrincipal.add(BigDecimal.valueOf(investment.principalAmt));
        netAmountOnEndDate =
            netAmountOnEndDate.add(
                BigDecimal.valueOf(investment.getAmtTillDate(lastInvestmentReportDate)));
        System.out.println(
            investment
                + " || interest-gathered: "
                + investment.getInterestGatheredTillDate(lastInvestmentReportDate));
      }
    }

    System.out.printf("net-principal: %s%n", netPrincipal);
    System.out.printf("net-amount: %s%n", netAmountOnEndDate);
    System.out.printf("net-profit: %s%n", netAmountOnEndDate.add(netPrincipal.negate()));
  }
}
