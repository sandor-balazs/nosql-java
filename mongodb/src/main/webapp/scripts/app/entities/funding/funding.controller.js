'use strict';

angular.module('ontimeApp')
    .controller('FundingController', function ($scope, $state, Funding) {

        $scope.fundings = [];
        $scope.loadAll = function() {
            Funding.query(function(result) {
               $scope.fundings = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.funding = {
                name: null,
                id: null
            };
        };
    });
