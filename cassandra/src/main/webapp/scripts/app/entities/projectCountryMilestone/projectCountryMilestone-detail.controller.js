'use strict';

angular.module('ontimeApp')
    .controller('ProjectCountryMilestoneDetailController', function ($scope, $rootScope, $stateParams, entity, ProjectCountryMilestone) {
        $scope.projectCountryMilestone = entity;
        $scope.load = function (id) {
            ProjectCountryMilestone.get({id: id}, function(result) {
                $scope.projectCountryMilestone = result;
            });
        };
        var unsubscribe = $rootScope.$on('ontimeApp:projectCountryMilestoneUpdate', function(event, result) {
            $scope.projectCountryMilestone = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
