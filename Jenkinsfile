pipeline {
    parameters {
        string(name: 'port', defaultValue: '22223', description: 'Port where news storage will be run')
    }
    options {
        timeout(time: 1, unit: 'HOURS')
    }
    agent {
        node {
            label 'master'
            customWorkspace "${workspace}/${env.BRANCH_NAME}"
        }
    }
    stages {
        stage("Build") {
            steps {
                dir('NewsStorage') {
                    sh 'docker build -t news-storage .'
                }
            }
        }
        stage("Deploy") {
            steps {
                script {
                    try {
                        sh "docker stop news-storage"
                        sh "docker rm news-storage"
                    } catch (exc) {
                        echo "Container doesn't exist!"
                    }
                }
                sh "docker volume create news-storage"
                sh "docker run --name news-storage -v news-storage:/data -p ${port}:8080 -d news-storage"
            }
        }
    }
}