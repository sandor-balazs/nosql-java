'use strict';

angular.module('ontimeApp')
    .controller('ProjectMilestoneController', function ($scope, $state, ProjectMilestone) {

        $scope.projectMilestones = [];
        $scope.loadAll = function() {
            ProjectMilestone.query(function(result) {
               $scope.projectMilestones = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.projectMilestone = {
                planned: null,
                actual: null,
                id: null
            };
        };
    });
