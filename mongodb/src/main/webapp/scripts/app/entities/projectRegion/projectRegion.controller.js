'use strict';

angular.module('ontimeApp')
    .controller('ProjectRegionController', function ($scope, $state, ProjectRegion) {

        $scope.projectRegions = [];
        $scope.loadAll = function() {
            ProjectRegion.query(function(result) {
               $scope.projectRegions = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.projectRegion = {
                id: null
            };
        };
    });
