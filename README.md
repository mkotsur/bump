```
I would like to go on holiday

(between 10.06.2016 and 20.08.2016 | in JUNE, JULY or AUGUST 2016)

for 2 to 3 days

with departure on 
  Friday (afternoon | from 10:00 till 20:00), or [...]

and return on
  Sunday afternoon;
```

```
  Text -> DSL PARSER -> TravelPreference

  TravelPreference -> GENERATE TRAVEL OPTIONS [Scala] -> List[TravelSpec]

  TravelSpec -> PYTHON/WEBDRIVER -> BestPrice
```  

## Setup

```
$ brew install jython chromedriver
$ jython -m ensurepip
$ /usr/local/Cellar/jython/2.7.0/libexec/bin/pip install selenium
```
