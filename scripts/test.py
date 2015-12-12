from selenium import webdriver
import momondo
import datetime
from calendar import Calendar

trip_duration = 14
range_begin = datetime.date(2016, 6, 11)
range_end = datetime.date(2017, 8, 11)

def dates_generator():
    trip_start_date = range_begin
    while(trip_start_date <= range_end):
        yield (trip_start_date, trip_start_date + datetime.timedelta(days=trip_duration))
        trip_start_date += datetime.timedelta(days=1)

driver = webdriver.PhantomJS()
pricewalker = momondo.PriceWalker(driver)

def process_result(date_dep, date_dest, price):
    print "%s - %s : %s" % (date_dep, date_dest, price)

pricewalker.find_best_prices(dates_generator(), "AMS", "DEL", process_result)

driver.quit()