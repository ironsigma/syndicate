<?php
namespace Cyniris\SyndicateBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity
 * @ORM\Table(name="state")
 */
class State {
    /**
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     * @ORM\Column(type="integer")
     */
    protected $id;

     /**
     * @ORM\Column(type="boolean")
     */
    protected $read;

     /**
     * @ORM\Column(type="boolean")
     */
    protected $stared;

    /**
     * @ORM\ManyToOne(targetEntity="Post")
     */
    protected $post;

    /**
     * @ORM\ManyToOne(targetEntity="User")
     */
    protected $user;

    /**
     * Get id
     *
     * @return integer 
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * Set read
     *
     * @param boolean $read
     * @return State
     */
    public function setRead($read)
    {
        $this->read = $read;

        return $this;
    }

    /**
     * Get read
     *
     * @return boolean 
     */
    public function getRead()
    {
        return $this->read;
    }

    /**
     * Set stared
     *
     * @param boolean $stared
     * @return State
     */
    public function setStared($stared)
    {
        $this->stared = $stared;

        return $this;
    }

    /**
     * Get stared
     *
     * @return boolean 
     */
    public function getStared()
    {
        return $this->stared;
    }

    /**
     * Set post
     *
     * @param \Cyniris\SyndicateBundle\Entity\Post $post
     * @return State
     */
    public function setPost(\Cyniris\SyndicateBundle\Entity\Post $post = null)
    {
        $this->post = $post;

        return $this;
    }

    /**
     * Get post
     *
     * @return \Cyniris\SyndicateBundle\Entity\Post 
     */
    public function getPost()
    {
        return $this->post;
    }

    /**
     * Set user
     *
     * @param \Cyniris\SyndicateBundle\Entity\User $user
     * @return State
     */
    public function setUser(\Cyniris\SyndicateBundle\Entity\User $user = null)
    {
        $this->user = $user;

        return $this;
    }

    /**
     * Get user
     *
     * @return \Cyniris\SyndicateBundle\Entity\User 
     */
    public function getUser()
    {
        return $this->user;
    }
}
