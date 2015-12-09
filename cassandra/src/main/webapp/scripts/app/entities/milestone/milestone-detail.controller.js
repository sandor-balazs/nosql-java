'use strict';

angular.module('ontimeApp')
    .controller('MilestoneDetailController', function ($scope, $rootScope, $stateParams, entity, Milestone) {
        $scope.milestone = entity;
        $scope.load = function (id) {
            Milestone.get({id: id}, function(result) {
                $scope.milestone = result;
            });
        };
        var unsubscribe = $rootScope.$on('ontimeApp:milestoneUpdate', function(event, result) {
            $scope.milestone = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
