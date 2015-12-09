'use strict';

angular.module('ontimeApp')
    .controller('AllocationController', function ($scope, $state, Allocation) {

        $scope.allocations = [];
        $scope.loadAll = function() {
            Allocation.query(function(result) {
               $scope.allocations = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.allocation = {
                fte: null,
                year: null,
                month: null,
                id: null
            };
        };
    });
