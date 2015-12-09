'use strict';

angular.module('ontimeApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('projectCountry', {
                parent: 'entity',
                url: '/projectCountrys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ontimeApp.projectCountry.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/projectCountry/projectCountrys.html',
                        controller: 'ProjectCountryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('projectCountry');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('projectCountry.detail', {
                parent: 'entity',
                url: '/projectCountry/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ontimeApp.projectCountry.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/projectCountry/projectCountry-detail.html',
                        controller: 'ProjectCountryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('projectCountry');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ProjectCountry', function($stateParams, ProjectCountry) {
                        return ProjectCountry.get({id : $stateParams.id});
                    }]
                }
            })
            .state('projectCountry.new', {
                parent: 'projectCountry',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/projectCountry/projectCountry-dialog.html',
                        controller: 'ProjectCountryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('projectCountry', null, { reload: true });
                    }, function() {
                        $state.go('projectCountry');
                    })
                }]
            })
            .state('projectCountry.edit', {
                parent: 'projectCountry',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/projectCountry/projectCountry-dialog.html',
                        controller: 'ProjectCountryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ProjectCountry', function(ProjectCountry) {
                                return ProjectCountry.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('projectCountry', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('projectCountry.delete', {
                parent: 'projectCountry',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/projectCountry/projectCountry-delete-dialog.html',
                        controller: 'ProjectCountryDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ProjectCountry', function(ProjectCountry) {
                                return ProjectCountry.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('projectCountry', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
