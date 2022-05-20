node {
    def selectedProjects = "${project_name}".split(',')

    // 拉取代码
    stage('pull code') {
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: 'a7cb7f55-c962-427f-a040-4c8c92219abc', url: 'git@github.com:Dreamer-07/guli_parent.git']]])
    }
    // 构建项目并上传的服务器上
    stage('project build') {
        steps {
            // (无 nexus) 安装公共依赖
            // sh 'mvn -f guli-common clean install'
            // (有 nexus) 安装公共依赖
            sh 'mvn -f guli-common clean deploy'
            for(int i=0;i<selectedProjects.size();i++){
                def currentProject = selectedProjects[i]
                // (无 nexus) 项目打包
                // sh 'mvn -f ${currentProject} clean install'
                // (有 nexus) 项目打包
                sh 'mvn -f ${currentProject} clean deploy'
            }
        }
    }
    // 构建后进行代码检查
    stage('code check') {
        def scannerHome = tool 'sonar-scanner-4.2'
        withSonarQubeEnv('sonar-server-7.7') {
            sh """
                ${scannerHome}/bin/sonar-scanner
            """
        }
    }

}