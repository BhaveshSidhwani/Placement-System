from sklearn.metrics.pairwise import cosine_similarity
from sklearn.feature_extraction.text import CountVectorizer
from flask import Flask, request, jsonify

def main(resume, job_description):

    text = [resume, job_description]

    cv = CountVectorizer()
    count_matrix = cv.fit_transform(text)

    # Use cosine_similarity
    print("\nSimilarity Scores : ")
    print(cosine_similarity(count_matrix))

    matchPercentage = cosine_similarity(count_matrix)[0][1] * 100
    matchPercentage = round(matchPercentage, 2)
    return str(matchPercentage)


app = Flask(__name__)

@app.route('/resume', methods=['POST'])
def resume():
    data = request.get_json(force=True)
    # accept files
    resume_content = data['resume']
    jD_content = data['job_description']

    res = main(resume_content, jD_content)
    resp = jsonify(value=res)

    return resp

if __name__ == '__main__':
    app.run()