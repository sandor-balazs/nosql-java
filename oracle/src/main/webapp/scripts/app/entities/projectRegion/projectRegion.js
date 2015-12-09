'use strict';

angular.module('ontimeApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('projectRegion', {
                parent: 'entity',
                url: '/projectRegions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ontimeApp.projectRegion.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/projectRegion/projectRegions.html',
                        controller: 'ProjectRegionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('projectRegion');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('projectRegion.detail', {
                parent: 'entity',
                url: '/projectRegion/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ontimeApp.projectRegion.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/projectRegion/projectRegion-detail.html',
                        controller: 'ProjectRegionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('projectRegion');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ProjectRegion', function($stateParams, ProjectRegion) {
                        return ProjectRegion.get({id : $stateParams.id});
                    }]
                }
            })
            .state('projectRegion.new', {
                parent: 'projectRegion',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/projectRegion/projectRegion-dialog.html',
                        controller: 'ProjectRegionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('projectRegion', null, { reload: true });
                    }, function() {
                        $state.go('projectRegion');
                    })
                }]
            })
            .state('projectRegion.edit', {
                parent: 'projectRegion',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/projectRegion/projectRegion-dialog.html',
                        controller: 'ProjectRegionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ProjectRegion', function(ProjectRegion) {
                                return ProjectRegion.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('projectRegion', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('projectRegion.delete', {
                parent: 'projectRegion',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/projectRegion/projectRegion-delete-dialog.html',
                        controller: 'ProjectRegionDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ProjectRegion', function(ProjectRegion) {
                                return ProjectRegion.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('projectRegion', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
