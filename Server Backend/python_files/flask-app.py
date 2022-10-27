from flask import Flask, request, jsonify
import pickle
import numpy as np
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.feature_extraction.text import CountVectorizer

app = Flask(__name__)

def calc(gender, degree_p, workex, quants, logical_reasoning, verbal, programming):

    # gender: Male=1; Female=0
    gender_label = 0

    # workex: Yes=1; No=0
    workex_label = 0

    if (gender == 'Male'):
        gender_label = 1

    if (workex == "true"):
        workex_label = 1

    my_data = [gender_label,
        degree_p,
        workex_label,
        quants,
        logical_reasoning,
        verbal,
        programming]

    model_name = 'random_forest_model.pkl'
    infile = open(model_name, 'rb')
    loaded_model = pickle.load(infile)
    infile.close()

    array = np.asarray([my_data])
    my_pred = loaded_model.predict_proba(array)

    neg = my_pred[0][0]
    pos = my_pred[0][1]

    return float( round(pos,2) )

@app.route('/predict', methods=['POST'])
def predict():
    data = request.get_json(force=True)

    gender = data["gender"]
    degree_p = data["degree_p"]
    workex = data["workex"]
    quants = data["quants"]
    logical_reasoning = data["logical_reasoning"]
    verbal = data["verbal"]
    programming = data["programming"]

    result = str( calc(gender, degree_p, workex, quants, logical_reasoning, verbal, programming) )

    resp = jsonify(value=result)

    return resp

def main(resume, job_description):

    text = [resume, job_description]

    cv = CountVectorizer()
    count_matrix = cv.fit_transform(text)

    # Use cosine_similarity
    print("\nSimilarity Scores : ")
    print(cosine_similarity(count_matrix))

    matchPercentage = cosine_similarity(count_matrix)[0][1]
    matchPercentage = round(matchPercentage, 2)
    return matchPercentage

@app.route('/resume', methods=['POST'])
def resume():
    data = request.get_json(force=True)
    # accept files
    resume_content = data['resume']
    jD_content = data['job_description']

    result = str(main(resume_content, jD_content))
    resp = jsonify(value=result)

    return resp

if __name__ == '__main__':
    app.run()