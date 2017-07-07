from pymongo import *
import pprint

pp = pprint.PrettyPrinter(indent=2)

client = MongoClient('localhost', 27017)
db = client.pressKeyDB
testingDataCollection = db["fuzzy-trial"]
userModelCollection = db['userModels']

testingData = testingDataCollection.find({'userEmail':'asd'},{'_id':0,'userEmail':0})
modelLoad = userModelCollection.find({'userEmail':'asd'},{'_id':0})


modelData = modelLoad.next()

keyStrokes = []

for keystroke in testingData:
	keyStrokes.append(keystroke)

testingDataList = []

for x in "abcdefghijklmnopqrstuvwxyz":
	characterTimes = []
	temp = {}
	for each in keyStrokes:
		if each['keystroke']['character'] == x:
			temp['character'] = x
			characterTimes.append(each['keystroke']['releaseTime'] - each['keystroke']['pressTime'])
	temp['times'] = characterTimes
	temp['character'] = x
	testingDataList.append(temp)

pos = 0
neg = 0
non = 0
majorPos = 0
majorNeg = 0
majorNon = 0
minorPos = 0
minorNeg = 0
minorNon = 0

for (characterValue, valueList) in zip(modelData['calculations'], testingDataList):
	currentCharacter = characterValue['character']
	average = characterValue['values']['avg']
	leftOut = characterValue['values']['leftOut']
	left = characterValue['values']['left']
	rightOut = characterValue['values']['rightOut']
	right = characterValue['values']['right']
	minVal = characterValue['values']['min']
	maxVal = characterValue['values']['max']
	# pp.pprint(currentCharacter)
	positiveCount = 0;
	negativeCount = 0;
	nonAcceptable = 0;
	totalCount = len(valueList['times'])
	if totalCount == 0:
		totalCount = 1
	for time in valueList['times']:
		if time > minVal and time < maxVal:
			positiveCount += 1
		elif (time < minVal and time > left):
				positiveCount += abs(time - minVal)/abs(minVal - left)
				negativeCount += abs(time - left)/abs(minVal - left)
		elif (time > maxVal and time < right):
			positiveCount += abs(time - maxVal)/abs(maxVal - right)
			negativeCount += abs(time - right)/abs(maxVal - right)
		elif (time < left and time > leftOut):
			negativeCount += abs(time - left)/abs(leftOut - left)
			nonAcceptable += abs(time - leftOut)/abs(leftOut - left)
		elif (time > right and time < rightOut):
			negativeCount += abs(time - right)/abs(rightOut - right)
			nonAcceptable += abs(time - rightOut)/abs(rightOut - right)
		elif (time < leftOut):
			nonAcceptable += abs(time - leftOut)/abs(left - right)
		elif (time > rightOut):
			nonAcceptable += abs(time - rightOut)/abs(left - right)


	positiveContribution = float("{0:.3f}".format(positiveCount/totalCount))
	negativeContribution = float("{0:.3f}".format(negativeCount/totalCount))
	nonContribution = float("{0:.3f}".format(nonAcceptable/totalCount))
	pos += positiveContribution
	neg += negativeContribution
	non += nonContribution
	if currentCharacter in "etaoiwnsrvbghldcum":
		majorPos += positiveContribution
		majorNon += nonContribution
		majorNeg += negativeContribution
	elif currentCharacter in "fpykxjqz":
		minorPos += positiveContribution
		minorNon += nonContribution
		minorNeg += negativeContribution
	# print("Character: " + currentCharacter)
	# print(positiveContribution, negativeContribution, nonContribution)

# print("----------Unadjusted----------")
print(float("{0:.3f}".format(pos/26)), float("{0:.3f}".format(neg/26)), float("{0:.3f}".format(non/26)))
finalPositive = (majorPos) + 0.5*(minorPos)
finalNegative = (majorNeg) + 0.5*(minorNeg)
finalNon = (majorNon) + 0.5*(minorNon)
if (finalPositive + finalNegative) == 0:
	pos = 0;
	neg = 0;
else:
	pos = finalPositive/(finalNegative + finalPositive + finalNon)
	neg = finalNegative/(finalNegative + finalPositive + finalNon)
non1 = finalNon/(finalNegative + finalPositive + finalNon)
print("\n-----------Final Results-----------")
# , float("{0:.3f}".format(non1))
print("Positive : " + str(float("{0:.3f}".format(pos))), "\nNegative : " + str(float("{0:.3f}".format(neg))))
if pos >= 0.9 and non1 <= 0.25:
	print ("User is Accepted")
else:
	print ("User is Rejected")

print("FRR : ", "{0:.2f}".format(1 - pos))
# pp.pprint(testingDataList)