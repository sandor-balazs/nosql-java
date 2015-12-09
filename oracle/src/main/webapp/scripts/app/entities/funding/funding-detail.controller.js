'use strict';

angular.module('ontimeApp')
    .controller('FundingDetailController', function ($scope, $rootScope, $stateParams, entity, Funding) {
        $scope.funding = entity;
        $scope.load = function (id) {
            Funding.get({id: id}, function(result) {
                $scope.funding = result;
            });
        };
        var unsubscribe = $rootScope.$on('ontimeApp:fundingUpdate', function(event, result) {
            $scope.funding = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
