'use strict';

angular.module('ontimeApp')
    .controller('RegionController', function ($scope, $state, Region) {

        $scope.regions = [];
        $scope.loadAll = function() {
            Region.query(function(result) {
               $scope.regions = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.region = {
                code: null,
                name: null,
                id: null
            };
        };
    });
