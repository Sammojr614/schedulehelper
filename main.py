from time import strftime
import tkinter as tk
from tkinter import *
import datetime as dt
import json

win = tk.Tk()
win.geometry("1280x800")
win.title("Schedule Helper")
startIdx = 0
f = open('scheduel.json')
data = json.load(f)
classNames = []
assignmentNames = []
startTimes = []
endTimes = []

def get_array(arrayName,arrayNumb):
    for item in data['assignments']:
        arrayName.append(item[arrayNumb])
    return arrayName
get_array(classNames,"class")
get_array(assignmentNames,'nameOf')
get_array(startTimes,'startTime')
get_array(endTimes,'endTime')
assignment_label = Label(win, text="test", font=('pf tempesta five', 20,'bold'))
assignment_label.place(x=200,y=475)
def assignment_clock():
    
    global startIdx
    now = dt.datetime.now()

    if now.hour >= startTimes[startIdx] and now.hour <= endTimes[startIdx]:
        assignment_label.config(text=f" Current assignment: {assignmentNames[startIdx]}\n For: {classNames[startIdx]} \n Please Complete By: {endTimes[startIdx] - 12} pm")
        assignment_label.after(1000,assignment_clock)
    elif now.hour > endTimes[startIdx]:
        if startIdx < len(assignmentNames):
            startIdx += 1
            assignment_clock(1000,assignment_clock)
        assignment_label.after(1000, assignment_clock)
    elif now.hour < startTimes[0]:
        assignment_label.config(text=f"Free Time Untill: {startTimes[0] - 12}")
        assignment_label.after(1000,assignment_clock)
    if now.hour > endTimes[len(endTimes) -1]:
        assignment_label.config(text="Done For Today!")
        assignment_label.after(assignment_clock)
       
        
        

def clock():
    string = strftime('%I:%M %p')
    clockLabel.config(text = string)
    clockLabel.after(1000,clock)
    
clockLabel = Label(win,font=('pf tempesta five',70,'bold'))
clockLabel.place(x=450,y=200)

clock()
assignment_clock()
win.mainloop()