from pymongo import *
import pprint

pp = pprint.PrettyPrinter(indent=4)

client = MongoClient('localhost', 27017)
db = client.pressKeyDB
collection = db["fuzzy-trial"]
insertCollection = db['userModels']

data = collection.find({},{'_id':0})

keyStrokes = []

for keystroke in data:
	if keystroke['userEmail'] == "asd":
		keyStrokes.append(keystroke)

dataList = []

for x in "abcdefghijklmnopqrstuvwxyz":
	times = []
	temp = {}
	for each in keyStrokes:
		if each['keystroke']['character'] == x:
			temp['character'] = x
			times.append(each['keystroke']['releaseTime'] - each['keystroke']['pressTime'])
	temp['times'] = times
	temp['character'] = x
	dataList.append(temp)

MIN = 100000*10000
MAX = 0

final = {}
valuesList = []
for item in dataList:
	timeDict = {}
	temp = {}
	min = MIN
	left = 0
	leftOut = 0
	right = 0
	rightOut = 0
	max = MAX
	total = 0
	for time in item['times']:
		if time < min:
			min = time
		if time > max:
			max = time
		total += time
	avg = int((min + max)/2)
	left = int(min - avg/8)
	leftOut = int(left - (avg/16))
	right = int(max + avg/8)
	rightOut = int(right + (avg/16))

	timeDict['leftOut'] = int(leftOut)
	timeDict['left'] = int(left)
	timeDict['min'] = int(min)
	timeDict['avg'] = int(avg)
	timeDict['max'] = int(max)
	timeDict['right'] = int(right)
	timeDict['rightOut'] = int(rightOut)

	temp['character'] = item['character']
	temp['values'] = timeDict

	valuesList.append(temp)

final['userEmail'] = "asd"
final['calculations'] = valuesList

string = "asd"

# db.getCollection('userModels').remove({ 'userEmail' : 'asd' });

ifExists = insertCollection.find({'userEmail':string}).count()
# print (ifExists)
if ifExists == 0:
	insertCollection.insert(final)
	print ("Modeling Complete")
else:
	print ("A model already exists!")

# pp.pprint(final)
# pp.pprint (dataList)
	