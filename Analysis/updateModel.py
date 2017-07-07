from pymongo import *
import pprint

pp = pprint.PrettyPrinter(indent=4)

client = MongoClient('localhost', 27017)
db = client.pressKeyDB

dataCollection = db["fuzzy-trial"]
modelCollection = db['userModels']

data = dataCollection.find({},{'_id':0})
modelLoad = modelCollection.find({'userEmail':'asd'},{'_id':0})
modelData = modelLoad.next()
dataListModel = modelData['calculations']

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
	# print (item['character'])
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
	# avg = total/len(item['times'])
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

# pp.pprint(valuesList)
# pp.pprint(dataListModel)


final = {}
tempValue = []
for (oldData, newData) in zip(dataListModel, valuesList):
	timeDict = {}
	temp = {}
	avg = 0.25*oldData['values']['avg'] + 0.75*newData['values']['avg']
	min = 0.25*oldData['values']['min'] + 0.75*newData['values']['min']
	max = 0.25*oldData['values']['max'] + 0.75*newData['values']['max']
	left = 0.25*oldData['values']['left'] + 0.75*newData['values']['left']
	right = 0.25*oldData['values']['right'] + 0.75*newData['values']['right']
	leftOut = 0.25*oldData['values']['leftOut']+ 0.75*newData['values']['leftOut']
	rightOut = 0.25*oldData['values']['rightOut']+ 0.75*newData['values']['rightOut']
	timeDict['leftOut'] = int(leftOut)
	timeDict['left'] = int(left)
	timeDict['min'] = int(min)
	timeDict['avg'] = int(avg)
	timeDict['max'] = int(max)
	timeDict['right'] = int(right)
	timeDict['rightOut'] = int(rightOut)

	temp['character'] = oldData['character']
	temp['values'] = timeDict

	tempValue.append(temp)

final['userEmail'] = "asd"
final['calculations'] = tempValue

string = "asd"

ifExists = modelCollection.find({'userEmail':string}).count()
if ifExists == 1:
	modelCollection.remove({ 'userEmail' : string })
	modelCollection.insert(final)
	print ("Model Updated")
else:
	modelCollection.insert(final)
