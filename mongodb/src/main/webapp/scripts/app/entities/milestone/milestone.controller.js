'use strict';

angular.module('ontimeApp')
    .controller('MilestoneController', function ($scope, $state, Milestone) {

        $scope.milestones = [];
        $scope.loadAll = function() {
            Milestone.query(function(result) {
               $scope.milestones = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.milestone = {
                name: null,
                appOrder: null,
                id: null
            };
        };
    });
