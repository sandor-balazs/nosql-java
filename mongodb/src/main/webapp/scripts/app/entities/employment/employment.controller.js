'use strict';

angular.module('ontimeApp')
    .controller('EmploymentController', function ($scope, $state, Employment) {

        $scope.employments = [];
        $scope.loadAll = function() {
            Employment.query(function(result) {
               $scope.employments = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.employment = {
                fte: null,
                startDate: null,
                endDate: null,
                id: null
            };
        };
    });
