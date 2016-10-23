app.controller('EmployeeHomeController', [ '$scope', 'sharedProperties',
		'$location', '$http', 'logout',
		function($scope, sharedProperties, $location, $http,logout) {
			console.log("Hello ");
			$scope.session = sharedProperties.getSession();
			if ($scope.session.name == '') {
				$location.path("/");
				console.log("Empty :(");
				return;
			}
			// $scope.page = 0;
			$scope.session = sharedProperties.getSession();
			console.log("In employee - " + $scope.session.name);

			$scope.welcome = "Hello Employee";
			var http = $http;
			
			// Pagination
			$scope.jobs=[];
			$scope.pageno=1;
			$scope.perpage=3;
			$scope.total=0;
			

			
	
			
			$scope.viewJobs=function(){
				
				console.log("Fetch jobs - ");
				// Fetch jobs posted by anyone
				http.get("/jobs/"+jobId+"/applications").success(function(data){
					$scope.applications=data;
					$scope.job=$scope.jobs[$index];
					console.log($scope.applications[0].jAppId);
					$scope.page=4;
					console.log((data));
				}).error(function(){
					console.log("Couldnt get applications");
				});
			};
			
			$scope.viewAppliedJobs=function(){
				http.get("/applications").success(function(data){
					$scope.applications=data;
					$scope.page=2;
					console.log((data));
					if(data==''){
						$scope.applyError="No applications to show";}
					else
						$scope.applyError="";
				}).error(function(){
					console.log("Couldnt get applications");
					$scope.applyError="Error retrieving applications";
				});
			}

			$scope.deleteApplication = function(index) {
				// $scope.jobs.
				console.log("Delete $index - " + index);
				console.log("Delete $index - " + $scope.applications[index].jAppId);
				http.delete("/applications/"+$scope.applications[index].jAppId).success(function(status){
					console.log("Deleted job");
					// $scope.jobs.splice($index, 1);
					$scope.listJobs();
				}).error(function() {
					console.log("didnt delete job");
				});
			}

			$scope.cancel = function() {
				$scope.page = 1;
				$scope.listJobs();
			}
			
			$scope.applyJob=function(index){
				console.log("Applying for job");
				var jobId=$scope.jobs[index].jId;
				http.post("jobs/"+jobId+"/applications").success(function(data){
					$scope.viewAppliedJobs();
				}).error(function(){
					console.log("Adding application failed");
				});
			}
			
			$scope.listJobs = function(pageno) {
				console.log("called listJobs");
				$scope.jobs=[];
				http.get("/jobs/"+$scope.perpage+"/"+pageno).success(function(data) {
					console.log("got jobs  - ");
					$scope.page = 1;
					$scope.jobs = data.jobs;
					$scope.total=data.totalPages;
					if(data.jobs==''){
						$scope.listError="No jobs to show";}
					else
						$scope.listError="";
				}).error(function() {
					console.log("didnt get jobs");
					$scope.listError="Error retrieving jobs";
					$scope.page=1;
				});
			};

			
			$scope.signoff=function(){
				logout();
				$location.path("/");
			}
			
			$scope.listJobs($scope.pageno);
		} ]);