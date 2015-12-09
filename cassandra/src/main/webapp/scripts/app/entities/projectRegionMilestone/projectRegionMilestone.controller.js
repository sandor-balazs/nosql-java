'use strict';

angular.module('ontimeApp')
    .controller('ProjectRegionMilestoneController', function ($scope, $state, ProjectRegionMilestone) {

        $scope.projectRegionMilestones = [];
        $scope.loadAll = function() {
            ProjectRegionMilestone.query(function(result) {
               $scope.projectRegionMilestones = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.projectRegionMilestone = {
                planned: null,
                actual: null,
                id: null
            };
        };
    });
