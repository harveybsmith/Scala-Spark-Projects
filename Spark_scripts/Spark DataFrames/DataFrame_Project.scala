// DATAFRAME PROJECT
// Use the Netflix_2011_2016.csv file to Answer and complete
// the commented tasks below!

// Start a simple Spark Session
import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder().getOrCreate()
// Load the Netflix Stock CSV File, have Spark infer the data types.
val df = spark.read.option("header","true").option("inferSchema","true").csv("Netflix_2011_2016.csv")

// What are the column names?
df.columns

// What does the Schema look like?
df.printSchema()

// Print out the first 5 columns.
df.head(5)

// Use describe() to learn about the DataFrame.
println("DataFrame statistics")
df.describe().show()

// Create a new dataframe with a column called HV Ratio that
// is the ratio of the High Price versus volume of stock traded
// for a day.
val df2 = df.withColumn("HV Ratio",df("High")/df("Volume"))

// What day had the Peak High in Price?
println("--------------------------")
println("Day with peak high price")
df2.orderBy($"High",desc).show(1)

// What is the mean of the Close column?
println("--------------------------")
println("Mean of the Close column")
df.select(mean("Close")).show()

// What is the max and min of the Volume column?
println("--------------------------")
println("Max Volume")
df2.select(max("Volume")).show()
println("Min Volume")
df2.select(max("Min")).show()

// For Scala/Spark $ Syntax
import spark.implicits._
// How many days was the Close lower than $ 600?
println("-------------------------------")
println("Days with Close lower than $600")
df.filter($"Close"<600).count()

// What percentage of the time was the High greater than $500 ?
println("------------------------------------")
println("Percentage of days with High greater than $500")
(df.filter($"High">500).count()*1.0 / df.count())*100

// What is the Pearson correlation between High and Volume?
println("--------------------------------------------")
println("Pearson correlation between High and Volume")
df.select(corr("High","Volume")).show()

// What is the max High per year?
println("--------------------------------------------")
println("Max High per year")
val yeardf = df.withColumn("Year",year(df("Date")))
val yearmaxs = yeardf.select($"Year",$"High").groupBy("Year").max()
val result = yearmaxs.select($"Year",$"max(High)")
result.orderBy("Year")

// What is the average Close for each Calender Month?
println("--------------------------------------------")
println("Average Close per Month")
val monthdf = df.withColumn("Month",month(df3("Date")))
val monthavg = monthdf.select($"Month","Close").groupBy("Month").mean()
monthavg.select($"Month",$"avg(Close)").orderBy("Month").show()
