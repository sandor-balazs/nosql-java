'use strict';

angular.module('ontimeApp')
    .controller('LocationController', function ($scope, $state, Location) {

        $scope.locations = [];
        $scope.loadAll = function() {
            Location.query(function(result) {
               $scope.locations = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.location = {
                name: null,
                address: null,
                id: null
            };
        };
    });
