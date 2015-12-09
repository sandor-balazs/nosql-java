'use strict';

angular.module('ontimeApp')
    .controller('ProjectCountryMilestoneController', function ($scope, $state, ProjectCountryMilestone) {

        $scope.projectCountryMilestones = [];
        $scope.loadAll = function() {
            ProjectCountryMilestone.query(function(result) {
               $scope.projectCountryMilestones = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.projectCountryMilestone = {
                planned: null,
                actual: null,
                id: null
            };
        };
    });
