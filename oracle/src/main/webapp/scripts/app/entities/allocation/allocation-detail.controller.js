'use strict';

angular.module('ontimeApp')
    .controller('AllocationDetailController', function ($scope, $rootScope, $stateParams, entity, Allocation, ProjectCountry, Employment, Skill) {
        $scope.allocation = entity;
        $scope.load = function (id) {
            Allocation.get({id: id}, function(result) {
                $scope.allocation = result;
            });
        };
        var unsubscribe = $rootScope.$on('ontimeApp:allocationUpdate', function(event, result) {
            $scope.allocation = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
