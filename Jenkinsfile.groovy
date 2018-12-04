pipeline {
  agent any
  stages {
    stage('Clean') {
      steps {
        deleteDir()
      }
    }
    stage('Fetch code') {
      steps {
        git url: scm.userRemoteConfigs[0].url, branch:'master', credentialsId: 'HKUCAB'
        script {
          VERSION = sh (returnStdout: true, script: 'git tag').trim()
        }
      }
    }
    stage('Compile and Run Unit Tests') {
      steps {
        sh "${WORKSPACE}/gradlew clean build"
      }
    }
  }  
}